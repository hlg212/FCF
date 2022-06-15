package io.github.hlg212.fcf.core.conf;

import com.google.common.collect.Sets;
import io.github.hlg212.fcf.annotation.CacheConditional;
import io.github.hlg212.fcf.constants.FrameCommonConstants;
import io.github.hlg212.fcf.core.cachex.ExRedisCacheManager;
import io.github.hlg212.fcf.core.conf.CacheConfig.FastJsonRedisSerializer;
import io.github.hlg212.fcf.core.properties.CommonProperties;
import io.github.hlg212.fcf.core.properties.ExRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
@CacheConditional
public class MultiRedisCacheManagerConfig implements ApplicationContextAware{

	@Autowired
	private CommonProperties commonProperties;

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	private final String cacheManagerBeanSuffix = FrameCommonConstants.CACHE_MANAGER_BEAN_SUFFIX;
	
	public void registerCacheManager(ApplicationContext applicationContext) {
		GenericApplicationContext c = (GenericApplicationContext) applicationContext;
		final DefaultListableBeanFactory b = (DefaultListableBeanFactory) c.getBeanFactory();
//		final ClientResources clientResources = b.getBean(ClientResources.class);
//		LettuceClientConfiguration clientConfig = getLettuceClientConfiguration(
//				clientResources, this.properties.getLettuce().getPool());
		
		Optional.ofNullable(commonProperties.getRedis()).orElse(Collections.emptyMap()).forEach((k, v) -> {
			
			String cacheManagerName = k + cacheManagerBeanSuffix;
			if( redisConnectionFactory instanceof LettuceConnectionFactory )
			{
				LettuceConnectionFactory defLettuceConnectionFactory = (LettuceConnectionFactory)redisConnectionFactory;
				RedisConfiguration redisConfiguration = copyRedisConfiguration(defLettuceConnectionFactory,v);

				LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisConfiguration, defLettuceConnectionFactory.getClientConfiguration());
				connectionFactory.afterPropertiesSet();
				ExRedisCacheManager.ExRedisCacheManagerBuilder builder = ExRedisCacheManager.builder2(connectionFactory);
				RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
						.serializeValuesWith(SerializationPair.fromSerializer(new FastJsonRedisSerializer()));
				builder.cacheDefaults(defaultCacheConfiguration);
				ExRedisCacheManager cacheManager = builder.build();

				b.registerSingleton(cacheManagerName, cacheManager);
				log.info("register CacheManager: {}",cacheManagerName);
			}
		});
	}

	private RedisConfiguration copyRedisConfiguration(LettuceConnectionFactory connectionFactory, ExRedisProperties exRedisProperties)
	{
		if( exRedisProperties.getSentinel() != null || connectionFactory.getSentinelConfiguration() != null )
		{
			return buildRedisSentinelConfiguration(connectionFactory,exRedisProperties);
		}
		else if( exRedisProperties.getCluster() != null ||  connectionFactory.getClusterConfiguration() != null )
		{
			return buildClusterConfiguration(connectionFactory,exRedisProperties);
		}
		return buildStandaloneConfiguration(connectionFactory,exRedisProperties);
	}
	private RedisConfiguration buildClusterConfiguration(LettuceConnectionFactory connectionFactory, ExRedisProperties exRedisProperties)
	{
		RedisClusterConfiguration old = connectionFactory.getClusterConfiguration();
		RedisClusterConfiguration redisConfiguration = null;

		RedisProperties.Cluster cluster = exRedisProperties.getCluster();
		if( cluster != null )
		{
			List<String> nodes = cluster.getNodes();

			if( nodes != null && !nodes.isEmpty())
			{
				redisConfiguration = new RedisClusterConfiguration(nodes);
			}
		}
		if( redisConfiguration == null )
		{
			redisConfiguration = new RedisClusterConfiguration();
		}
		if( cluster != null )
		{
			redisConfiguration.setMaxRedirects(cluster.getMaxRedirects());
		}
		redisConfiguration.setPassword(exRedisProperties.getPassword());
		if( old != null ) {
			if (redisConfiguration.getClusterNodes() == null) {
				redisConfiguration.setClusterNodes(old.getClusterNodes());
			}
			if ( StringUtils.isEmpty(exRedisProperties.getPassword() ) ) {
				redisConfiguration.setPassword(old.getPassword());
			}
			if (redisConfiguration.getMaxRedirects() == null) {
				redisConfiguration.setMaxRedirects(old.getMaxRedirects());
			}
		}
		return redisConfiguration;
	}
	private RedisConfiguration buildStandaloneConfiguration(LettuceConnectionFactory connectionFactory, ExRedisProperties exRedisProperties)
	{
		RedisStandaloneConfiguration old = connectionFactory.getStandaloneConfiguration();
		RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();

		redisConfiguration.setDatabase(exRedisProperties.getDatabase());
		redisConfiguration.setHostName(exRedisProperties.getHost());
		redisConfiguration.setPassword(exRedisProperties.getPassword());
		redisConfiguration.setPort(exRedisProperties.getPort());
		redisConfiguration.setDatabase(exRedisProperties.getDatabase());
		if( old != null ) {
			if (StringUtils.isEmpty(redisConfiguration.getHostName())) {
				redisConfiguration.setHostName(old.getHostName());
			}
			if ( StringUtils.isEmpty(exRedisProperties.getPassword() ) ) {
				redisConfiguration.setPassword(old.getPassword());
			}
			if (redisConfiguration.getPort() == 0)
			{
				redisConfiguration.setPort(old.getPort());
			}
		}

		return redisConfiguration;
	}
	private RedisConfiguration buildRedisSentinelConfiguration(LettuceConnectionFactory connectionFactory, ExRedisProperties exRedisProperties)
	{
		RedisSentinelConfiguration old = connectionFactory.getSentinelConfiguration();
		RedisSentinelConfiguration redisConfiguration = null;

		RedisProperties.Sentinel sentinel = exRedisProperties.getSentinel();
		if( sentinel != null )
		{
			List<String> nodes = sentinel.getNodes();

			if( nodes != null && !nodes.isEmpty())
			{
				redisConfiguration = new RedisSentinelConfiguration(sentinel.getMaster(), Sets.newLinkedHashSet(nodes));
			}
		}
		if( redisConfiguration == null )
		{
			redisConfiguration = new RedisSentinelConfiguration();
		}
		redisConfiguration.setDatabase(exRedisProperties.getDatabase());
		redisConfiguration.setPassword(exRedisProperties.getPassword());
		if( old != null ) {
			if (redisConfiguration.getMaster() == null) {
				redisConfiguration.setMaster(old.getMaster());
			}
			if (redisConfiguration.getSentinels() == null || redisConfiguration.getSentinels().isEmpty()) {
				redisConfiguration.setSentinels(old.getSentinels());
			}
			if ( StringUtils.isEmpty(exRedisProperties.getPassword() ) ) {
				redisConfiguration.setPassword(old.getPassword());
			}
		}
		return redisConfiguration;
	}






	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.registerCacheManager(applicationContext);
	}



}
