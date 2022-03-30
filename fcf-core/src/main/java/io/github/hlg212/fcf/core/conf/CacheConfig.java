package  io.github.hlg212.fcf.core.conf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import  io.github.hlg212.fcf.annotation.CacheConditional;
import  io.github.hlg212.fcf.cache.Constants;
import  io.github.hlg212.fcf.constants.FrameCommonConstants;
import  io.github.hlg212.fcf.core.cache.CusRedisCacheConfiguration;
import  io.github.hlg212.fcf.core.cachex.ExCacheErrorHandler;
import  io.github.hlg212.fcf.core.cachex.ExCacheResolver;
import  io.github.hlg212.fcf.core.cachex.ExRedisCacheManager;
import  io.github.hlg212.fcf.util.ThreadLocalHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisCommands;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 注入Cache相关对象
 *
 * @author  huangligui
 * @create: 2019-03-01 13:23
 */
@Slf4j
@Configuration
@EnableCaching(order=100+1)
@CacheConditional
public class CacheConfig extends CachingConfigurerSupport{
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Autowired(required =false)
	private List<CusRedisCacheConfiguration> cusRedisCacheConfigurations;
	
	@Bean(Constants.CacheManager.DefCacheManager)
	@Primary
	public RedisCacheManager createCacheManager() {
		ExRedisCacheManager.ExRedisCacheManagerBuilder builder = ExRedisCacheManager
				.builder2(redisConnectionFactory);
		//RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory);
		RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofDays(1))
				//.disableCachingNullValues()
				.serializeValuesWith(SerializationPair.fromSerializer(fastJsonRedisSerializer()));
		builder.cacheDefaults(defaultCacheConfiguration);
		if( cusRedisCacheConfigurations != null )
		{
			Map<String,RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

			cusRedisCacheConfigurations.forEach(cusRedisCacheConfiguration -> {
				cacheConfigurations.put(cusRedisCacheConfiguration.getName(),cusRedisCacheConfiguration.getRedisCacheConfiguration());
			});

			builder.withInitialCacheConfigurations(cacheConfigurations);
		}
		return builder.transactionAware().build();
	}


	@Override
	public CacheResolver cacheResolver() {
		try {
			return new ExCacheResolver(this.createCacheManager());
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	@Override
	public CacheManager cacheManager() {
		return this.createCacheManager();
	}
	
	@Override
	public CacheErrorHandler errorHandler() {
		return this.htcfCacheErrorHandler();
	}
	
	@Bean
	public ExCacheErrorHandler htcfCacheErrorHandler() {
		return new ExCacheErrorHandler();
	}
	
	@Bean
	@Lazy
	public RedisCommands redisCommands() {
		return redisConnectionFactory.getConnection();
	}

	@Bean
	public RedisSerializer fastJsonRedisSerializer()
	{
		return new FastJsonRedisSerializer();
	}

	static class FastJsonRedisSerializer implements RedisSerializer<Object>{
		
		private final static ParserConfig defaultRedisConfig = new ParserConfig();
		
		static { defaultRedisConfig.setAutoTypeSupport(true);}

		    
		@Override
		public byte[] serialize(Object object) throws SerializationException {
			if (object == null) {
	            return new byte[0];
	        }
			try {
				return JSON.toJSONBytes(object, SerializerFeature.WriteClassName);
			} catch (Exception e) {
				log.error("cache value 序列化异常", e);
			}
			return new byte[0];
		}

		@Override
		public Object deserialize(byte[] bytes) throws SerializationException {
			if (bytes == null || bytes.length == 0) {
				return null;
		    }
			Method method = ThreadLocalHelper.get(FrameCommonConstants.REQUEST_CACHE_METHOD);
			try {
				if(method == null) {
					/*log.warn("未能正常获取到cache mehod对象,请检查CacheMethodInterceptor拦截逻辑,此处默认根据autoType进行反序列化操作.");
					return JSON.parseObject(new String(bytes, IOUtils.UTF8), Object.class, defaultRedisConfig);*/
//					log.info("未能正常获取到cache mehod对象,可检查CacheMethodInterceptor拦截逻辑,可能原因当前method上未加cache注解,此处默认返回null让其执行方法原有逻辑.");
					return null;
				}
				Type type = method.getGenericReturnType();
				if(type instanceof TypeVariable) {
					return JSON.parseObject(new String(bytes, IOUtils.UTF8), Object.class, defaultRedisConfig);
				}
				return JSON.parseObject(new String(bytes, IOUtils.UTF8), method.getGenericReturnType(), defaultRedisConfig, Feature.IgnoreAutoType);
			} catch (Exception e) {
				log.error("cache value 反序列化异常", e);
			}
			return null;
		}

	}
	
}
