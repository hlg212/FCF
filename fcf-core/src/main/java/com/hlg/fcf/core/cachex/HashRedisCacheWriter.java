package com.hlg.fcf.core.cachex;

import java.time.Duration;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * hash结构 redis的读取类默认实现
 * 
 * @author changwei
 * @date 2019年1月10日
 */
public class HashRedisCacheWriter extends DefaultRedisCacheWriter {

	/**
	 * @param connectionFactory must not be {@literal null}.
	 */
	HashRedisCacheWriter(RedisConnectionFactory connectionFactory) {
		this(connectionFactory, Duration.ZERO);
	}

	/**
	 * @param connectionFactory must not be {@literal null}.
	 * @param sleepTime sleep time between lock request attempts. Must not be {@literal null}. Use {@link Duration#ZERO}
	 *          to disable locking.
	 */
	HashRedisCacheWriter(RedisConnectionFactory connectionFactory, Duration sleepTime) {
		super(connectionFactory, sleepTime);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.cache.RedisCacheWriter#put(java.lang.String, byte[], byte[], java.time.Duration)
	 */
	@Override
	public void put(String name, byte[] key, byte[] value, @Nullable Duration ttl) {

		Assert.notNull(name, "Name must not be null!");
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");

		execute(name, connection -> {

			if (shouldExpireWithin(ttl)) {
				//此处还需设置过期时间
				//connection.ttl(name.getBytes(), Expiration.from(ttl.toMillis(), TimeUnit.MILLISECONDS) );
				Long ettl = connection.ttl(name.getBytes());

				connection.hSet(name.getBytes(), key, value);
				if( ettl == null || ettl <= 0 )
				{
					connection.expire(name.getBytes(),ttl.getSeconds());
				}

			} else {
				connection.hSet(name.getBytes(), key, value);
			}

			return "OK";
		});
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.cache.RedisCacheWriter#get(java.lang.String, byte[])
	 */
	@Override
	public byte[] get(String name, byte[] key) {

		Assert.notNull(name, "Name must not be null!");
		Assert.notNull(key, "Key must not be null!");

		return execute(name, connection -> connection.hGet(name.getBytes(), key));
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.cache.RedisCacheWriter#putIfAbsent(java.lang.String, byte[], byte[], java.time.Duration)
	 */
	@Override
	public byte[] putIfAbsent(String name, byte[] key, byte[] value, @Nullable Duration ttl) {

		Assert.notNull(name, "Name must not be null!");
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");

		return execute(name, connection -> {

			if (isLockingCacheWriter()) {
				doLock(name, connection);
			}

			try {
				if (connection.setNX(key, value)) {

					if (shouldExpireWithin(ttl)) {
						connection.pExpire(key, ttl.getSeconds());
					}
					return null;
				}

				return connection.get(key);
			} finally {

				if (isLockingCacheWriter()) {
					doUnlock(name, connection);
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.cache.RedisCacheWriter#remove(java.lang.String, byte[])
	 */
	@Override
	public void remove(String name, byte[] key) {

		Assert.notNull(name, "Name must not be null!");
		Assert.notNull(key, "Key must not be null!");

		execute(name, connection -> connection.hDel(name.getBytes(), key));
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.cache.RedisCacheWriter#clean(java.lang.String, byte[])
	 */
	@Override
	public void clean(String name, byte[] pattern) {

		Assert.notNull(name, "Name must not be null!");
		Assert.notNull(pattern, "Pattern must not be null!");

		execute(name, connection -> {

			boolean wasLocked = false;

			try {

				if (isLockingCacheWriter()) {
					doLock(name, connection);
					wasLocked = true;
				}
//				byte[][] keys = Optional.ofNullable(connection.keys(pattern)).orElse(Collections.emptySet())
//						.toArray(new byte[0][]);
//
//				if (keys.length > 0) {
//					connection.hDel(name.getBytes(), keys);
//				}
				connection.del(name.getBytes());
			} finally {

				if (wasLocked && isLockingCacheWriter()) {
					doUnlock(name, connection);
				}
			}

			return "OK";
		});
	}
}
