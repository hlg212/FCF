package com.hlg.fcf.core.cachex;

import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoneCache extends RedisCache implements Cache{
	
	public static final String CACHE_NAME = "NONE_CACHE";
	
	protected NoneCache(RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
		super(CACHE_NAME, cacheWriter, cacheConfig);
	}


	@Override
	public String getName() {
		return CACHE_NAME;
	}

	@Override
	public RedisCacheWriter getNativeCache() {
		log.trace("invoke getNativeCache");
		return null;
	}

	@Override
	public ValueWrapper get(Object key) {
		log.trace("invoke get:{}", key);
		return null;
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		log.trace("invoke get:{}, type:{}", key, type);
		return null;
	}

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		log.trace("invoke get key:{}, valueLoader:{}", key, valueLoader);
		return null;
	}

	@Override
	public void put(Object key, Object value) {
		log.trace("invoke put key:{}, value:{}", key, value);
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		log.trace("invoke putIfAbsent key:{}, value:{}", key, value);
		return null;
	}

	@Override
	public void evict(Object key) {
		log.trace("invoke evict key:{}", key);
	}

	@Override
	public void clear() {
		log.trace("invoke clear");
	}

}
