package  io.github.hlg212.fcf.core.cachex;

import  io.github.hlg212.fcf.cache.Constants;
import  io.github.hlg212.fcf.util.CacheNameHelper;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ExRedisCacheManager extends RedisCacheManager{
	
	private NoneCache noneCache = null;

	public ExRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
			Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
		super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
		this.noneCache = new NoneCache(cacheWriter, defaultCacheConfiguration);
	}

	@Override
	public Collection<String> getCacheNames(){
		return CacheNameHelper.getCacheTypeMap().get(Constants.CacheManager.DefCacheManager);
	}
	
	@Override
	protected RedisCache getMissingCache(String name) {
		if(NoneCache.CACHE_NAME.equals(name)) {
			return noneCache;
		}
		return super.getMissingCache(name);
	}

	@Override
	public Cache getCache(String name) {
 		Cache cache = super.getCache(name);
		if( cache instanceof  TransactionAwareCacheDecorator) {
			Cache targetCache = ((TransactionAwareCacheDecorator) cache).getTargetCache();
			if ((!(targetCache instanceof CacheWapper) && !(targetCache instanceof NoneCache))) {
				return new CacheWapper(cache);
			}
		}
		else if ( !(cache instanceof CacheWapper) && !(cache instanceof NoneCache) ) {
			return new CacheWapper(cache);
		}
		return cache;
	}


	public static ExRedisCacheManagerBuilder builder2(RedisCacheWriter cacheWriter) {

		Assert.notNull(cacheWriter, "CacheWriter must not be null!");

		return ExRedisCacheManagerBuilder.fromCacheWriter(cacheWriter);
	}
	
	/**
	 * Entry point for builder style {@link RedisCacheManager} configuration.
	 *
	 * @param connectionFactory must not be {@literal null}.
	 *   new {@link RedisCacheManagerBuilder}.
	 */
	public static ExRedisCacheManagerBuilder builder2(RedisConnectionFactory connectionFactory) {

		Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");

		return ExRedisCacheManagerBuilder.fromConnectionFactory(connectionFactory);
	}
	
	public static class ExRedisCacheManagerBuilder{
		
		private RedisCacheWriter cacheWriter;
		private RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		private final Map<String, RedisCacheConfiguration> initialCaches = new LinkedHashMap<>();
		private boolean enableTransactions;
		boolean allowInFlightCacheCreation = true;
		
		private ExRedisCacheManagerBuilder(RedisCacheWriter cacheWriter) {
			this.cacheWriter = cacheWriter;
		}


		public static ExRedisCacheManagerBuilder fromConnectionFactory(RedisConnectionFactory connectionFactory) {

			Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");

			return ExRedisCacheManager.builder2(new HashRedisCacheWriter(connectionFactory));
		}
		

		public static ExRedisCacheManagerBuilder fromCacheWriter(RedisCacheWriter cacheWriter) {

			Assert.notNull(cacheWriter, "CacheWriter must not be null!");

			return new ExRedisCacheManagerBuilder(cacheWriter);
		}


		public ExRedisCacheManagerBuilder cacheDefaults(RedisCacheConfiguration defaultCacheConfiguration) {

			Assert.notNull(defaultCacheConfiguration, "DefaultCacheConfiguration must not be null!");

			this.defaultCacheConfiguration = defaultCacheConfiguration;

			return this;
		}


		public ExRedisCacheManagerBuilder transactionAware() {

			this.enableTransactions = true;

			return this;
		}


		public ExRedisCacheManagerBuilder initialCacheNames(Set<String> cacheNames) {

			Assert.notNull(cacheNames, "CacheNames must not be null!");

			Map<String, RedisCacheConfiguration> cacheConfigMap = new LinkedHashMap<>(cacheNames.size());
			cacheNames.forEach(it -> cacheConfigMap.put(it, defaultCacheConfiguration));

			return withInitialCacheConfigurations(cacheConfigMap);
		}

		public ExRedisCacheManagerBuilder withInitialCacheConfigurations(
				Map<String, RedisCacheConfiguration> cacheConfigurations) {

			Assert.notNull(cacheConfigurations, "CacheConfigurations must not be null!");
			cacheConfigurations.forEach((cacheName, configuration) -> Assert.notNull(configuration,
					String.format("RedisCacheConfiguration for cache %s must not be null!", cacheName)));

			this.initialCaches.putAll(cacheConfigurations);

			return this;
		}

		public ExRedisCacheManagerBuilder disableCreateOnMissingCache() {

			this.allowInFlightCacheCreation = false;
			return this;
		}

		/**
		 * Create new instance of {@link RedisCacheManager} with configuration options applied.
		 *
		 *   new instance of {@link RedisCacheManager}.
		 */
		public ExRedisCacheManager build() {

			ExRedisCacheManager cm = new ExRedisCacheManager(cacheWriter, defaultCacheConfiguration, initialCaches,
					allowInFlightCacheCreation);

			cm.setTransactionAware(enableTransactions);

			return cm;
		}
	}
}
