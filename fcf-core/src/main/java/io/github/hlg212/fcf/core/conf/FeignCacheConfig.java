package  io.github.hlg212.fcf.core.conf;

import  io.github.hlg212.fcf.annotation.CacheConditional;
import  io.github.hlg212.fcf.constants.FrameCommonConstants;
import  io.github.hlg212.fcf.core.cachex.ExRedisCacheManager;
import  io.github.hlg212.fcf.core.conf.CacheConfig.HtcfFastJsonRedisSerializer;
import  io.github.hlg212.fcf.core.properties.CommonProperties;
import  io.github.hlg212.fcf.service.FrameService;
import  io.github.hlg212.fcf.util.ThreadLocalHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.config.CacheManagementConfigUtils;
import org.springframework.cache.interceptor.*;
import org.springframework.cache.interceptor.CacheableOperation.Builder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.lang.Nullable;
import org.springframework.util.SystemPropertyUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * feign接口缓存自动切换
 *
 * @author  huangligui
 * @create: 2019-03-01 13:23
 */
@Slf4j
@Configuration
@CacheConditional
public class FeignCacheConfig implements ApplicationContextAware{

	@Autowired
	private CommonProperties commonProperties;

	@Autowired
	private Environment environment;

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	
	@Resource(name=CacheManagementConfigUtils.CACHE_ADVISOR_BEAN_NAME)
	private BeanFactoryCacheOperationSourceAdvisor beanFactoryCacheOperationSourceAdvisor;
	
	private final String cacheManagerBeanSuffix = FrameCommonConstants.CACHE_MANAGER_BEAN_SUFFIX;
	
	private final Set<String> cacheManagerSystemNameSet = new HashSet<>();

	
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
				RedisConfiguration redisConfiguration = copyRedisConfiguration(defLettuceConnectionFactory,v.getDatabase());

				LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisConfiguration, defLettuceConnectionFactory.getClientConfiguration());
				connectionFactory.afterPropertiesSet();
				ExRedisCacheManager.ExRedisCacheManagerBuilder builder = ExRedisCacheManager.builder2(connectionFactory);
				RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
						.serializeValuesWith(SerializationPair.fromSerializer(new HtcfFastJsonRedisSerializer()));
				builder.cacheDefaults(defaultCacheConfiguration);
				ExRedisCacheManager cacheManager = builder.transactionAware().build();
				b.registerSingleton(cacheManagerName, cacheManager);
				cacheManagerSystemNameSet.add(k);
			}


		});
	}

	private RedisConfiguration copyRedisConfiguration(LettuceConnectionFactory connectionFactory,int database)
	{
		if( connectionFactory.getSentinelConfiguration() != null )
		{
			RedisSentinelConfiguration old = connectionFactory.getSentinelConfiguration();
			RedisSentinelConfiguration redisConfiguration = new RedisSentinelConfiguration();
			redisConfiguration.setMaster(old.getMaster());
			redisConfiguration.setSentinels(old.getSentinels());
			redisConfiguration.setDatabase(database);
			redisConfiguration.setPassword(old.getPassword());
			return redisConfiguration;
		}
		else if( connectionFactory.getClusterConfiguration() != null )
		{
			RedisClusterConfiguration old = connectionFactory.getClusterConfiguration();
			RedisClusterConfiguration redisConfiguration = new RedisClusterConfiguration();
			redisConfiguration.setClusterNodes(old.getClusterNodes());
			if( old.getMaxRedirects() != null ){
				redisConfiguration.setMaxRedirects(old.getMaxRedirects());
			}
			redisConfiguration.setPassword(old.getPassword());
			return redisConfiguration;
		}
		else if( connectionFactory.getStandaloneConfiguration() != null )
		{
			RedisStandaloneConfiguration old = connectionFactory.getStandaloneConfiguration();
			RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
			redisStandaloneConfiguration.setHostName(old.getHostName());
			redisStandaloneConfiguration.setPassword(old.getPassword());
			redisStandaloneConfiguration.setPort(old.getPort());
			redisStandaloneConfiguration.setDatabase(database);
			return redisStandaloneConfiguration;
		}
		log.warn("not impl RedisConfiguration");
		return null;
	}
	
	@PostConstruct
	public void rewriteCacheAnnotationScan() {
		CacheOperationSource arr[] = new CacheOperationSource[] { new HtcfAnnotationCacheOperationSource()};
		CompositeCacheOperationSource c = new CompositeCacheOperationSource(arr);
		beanFactoryCacheOperationSourceAdvisor.setCacheOperationSource(c);
		CacheInterceptor cacheInterceptor = (CacheInterceptor) beanFactoryCacheOperationSourceAdvisor.getAdvice();
		cacheInterceptor.setCacheOperationSources(c);
	}
	

	
	class HtcfAnnotationCacheOperationSource extends AnnotationCacheOperationSource{

		private static final long serialVersionUID = 1L;
		
		private Map<Method, Method> primitiveMethodMap = new ConcurrentHashMap<>();
		private Map<Method, Boolean> finishedMethodMap = new ConcurrentHashMap<>();
		
		@Override
		protected Collection<CacheOperation> findCacheOperations(Method method) {
			Collection<CacheOperation> result = super.findCacheOperations(method);
			result = Optional.ofNullable(result).orElse(Collections.emptyList());
			if(result.size() == 0) {
				return null;
			}

			Class cacheTargetClass = ThreadLocalHelper.get("cacheTargetClass");
			if( cacheTargetClass != null )
			{
				result = result.stream().map(o -> this.rewriteCacheOperation(o)).filter(o-> o != null).collect(Collectors.toList());
				return result;
			}

			method = primitiveMethodMap.get(method) != null ? primitiveMethodMap.get(method) : method;
			log.trace("scan cache class:{},method :{}, result:{},config:{}", method.getDeclaringClass().getName(), method.getName(), result.size(), method.getDeclaringClass().getAnnotation(org.springframework.cache.annotation.CacheConfig.class));
			Class<?> clazz = method.getDeclaringClass();
			FeignClient feignClient = clazz.getAnnotation(FeignClient.class);
			if(feignClient != null && StringUtils.isNotBlank(feignClient.path())) {
				String path = getPath(feignClient);
				if(cacheManagerSystemNameSet.contains(path)) {
					String cacheManager = path + cacheManagerBeanSuffix;
					log.info("do retry {}.{} set cacheManager value to:{}", method.getDeclaringClass().getName(), method.getName(), cacheManager);
					result = result.stream().map(o -> this.rewriteCacheManager(o, cacheManager)).collect(Collectors.toList());
				} else {
					log.warn("cacheManagerSystemNameSet not exists {}, please check @FeignClient path property value or Manual setting cacheManager property value", path);
				}
			}
//			CacheNameHolder holder = SpringHelper.getBean(CacheNameHolder.class);
//			result.forEach(cacheOperation -> {
//				cacheOperation.getCacheNames().forEach(cacheName ->{
//					holder.add(cacheOperation.getCacheManager(),cacheName);
//				});
//			});
			return result;
		}



		private CacheOperation rewriteCacheOperation(CacheOperation operation) {
			if( operation.getCacheNames().isEmpty() )
			{
				org.springframework.cache.interceptor.CacheOperation.Builder builder = null;
					if(operation instanceof CacheableOperation) {
						CacheableOperation co = (CacheableOperation) operation;
						Builder b = new CacheableOperation.Builder();
						b.setCacheManager(co.getCacheManager());
						b.setCacheResolver(co.getCacheResolver());
						b.setCacheNames(co.getCacheNames().toArray(new String[0]));
						b.setCondition(co.getCondition());
						b.setKey(co.getKey());
						b.setKeyGenerator(co.getKeyGenerator());
						b.setName(co.getName());
						b.setSync(co.isSync());
						b.setUnless(co.getUnless());
						builder = b;
					}else if(operation instanceof CacheEvictOperation) {
						CacheEvictOperation ceo = (CacheEvictOperation) operation;
						org.springframework.cache.interceptor.CacheEvictOperation.Builder b = new CacheEvictOperation.Builder();
						b.setBeforeInvocation(ceo.isBeforeInvocation());
						b.setCacheManager(ceo.getCacheManager());
						b.setCacheNames(ceo.getCacheNames().toArray(new String[0]));
						b.setCacheResolver(ceo.getCacheResolver());
						b.setCacheWide(ceo.isCacheWide());
						b.setCondition(ceo.getCondition());
						b.setKey(ceo.getKey());
						b.setKeyGenerator(ceo.getKeyGenerator());
						b.setName(ceo.getName());
						builder = b;
					}else if(operation instanceof CachePutOperation) {
						CachePutOperation cpo = (CachePutOperation) operation;
						org.springframework.cache.interceptor.CachePutOperation.Builder b = new CachePutOperation.Builder();
						b.setCacheManager(cpo.getCacheManager());
						b.setCacheNames(cpo.getCacheNames().toArray(new String[0]));
						b.setCacheResolver(cpo.getCacheResolver());
						b.setCondition(cpo.getCondition());
						b.setKey(cpo.getKey());
						b.setKeyGenerator(cpo.getKeyGenerator());
						b.setName(cpo.getName());
						b.setUnless(cpo.getUnless());
						builder = b;
					}
				applyCacheConfig(operation,builder);
				if( builder.getCacheNames().isEmpty() )
				{
					return null;
				}

				return builder.build();
			}
			return operation;
		}


		private void applyCacheConfig(CacheOperation operation,org.springframework.cache.interceptor.CacheOperation.Builder b)
		{

			Class cacheTargetClass = ThreadLocalHelper.get("cacheTargetClass");
			if( cacheTargetClass != null ) {
				org.springframework.cache.annotation.CacheConfig annotation = AnnotatedElementUtils.findMergedAnnotation(cacheTargetClass, org.springframework.cache.annotation.CacheConfig.class);
				if (annotation == null) {
					return;
				}

				if (StringUtils.isEmpty(operation.getCacheManager())) {
					b.setCacheManager(annotation.cacheManager());
				}

				if (StringUtils.isEmpty(operation.getCacheResolver())) {
					b.setCacheResolver(annotation.cacheResolver());
				}

				if (StringUtils.isEmpty(operation.getKeyGenerator())) {
					b.setKeyGenerator(annotation.keyGenerator());
				}

				if (operation.getCacheNames().isEmpty() ) {
					b.setCacheNames(annotation.cacheNames());
				}
			}
		}


		private CacheOperation rewriteCacheManager(CacheOperation operation, String cacheManager) {
			if(operation instanceof CacheableOperation) {
				CacheableOperation co = (CacheableOperation) operation;
				Builder b = new CacheableOperation.Builder();
				b.setCacheManager(StringUtils.isBlank(co.getCacheManager())? cacheManager : co.getCacheManager());
				Optional.ofNullable(co.getCacheNames()).orElse(Collections.emptySet()).forEach(c -> b.setCacheName(c));
				b.setCacheResolver(co.getCacheResolver());
				b.setCondition(co.getCondition());
				b.setKey(co.getKey());
				b.setKeyGenerator(co.getKeyGenerator());
				b.setName(co.getName());
				b.setSync(co.isSync());
				b.setUnless(co.getUnless());
				return b.build();
			}else if(operation instanceof CacheEvictOperation) {
				CacheEvictOperation ceo = (CacheEvictOperation) operation;
				org.springframework.cache.interceptor.CacheEvictOperation.Builder b = new CacheEvictOperation.Builder();
				b.setBeforeInvocation(ceo.isBeforeInvocation());
				b.setCacheManager(StringUtils.isBlank(ceo.getCacheManager())? cacheManager : ceo.getCacheManager());
				Optional.ofNullable(ceo.getCacheNames()).orElse(Collections.emptySet()).forEach(c -> b.setCacheName(c));
				b.setCacheResolver(ceo.getCacheResolver());
				b.setCacheWide(ceo.isCacheWide());
				b.setCondition(ceo.getCondition());
				b.setKey(ceo.getKey());
				b.setKeyGenerator(ceo.getKeyGenerator());
				b.setName(ceo.getName());
				return b.build();
			}else if(operation instanceof CachePutOperation) {
				CachePutOperation cpo = (CachePutOperation) operation;
				org.springframework.cache.interceptor.CachePutOperation.Builder b = new CachePutOperation.Builder();
				b.setCacheManager(StringUtils.isBlank(cpo.getCacheManager())? cacheManager : cpo.getCacheManager());
				Optional.ofNullable(cpo.getCacheNames()).orElse(Collections.emptySet()).forEach(c -> b.setCacheName(c));
				b.setCacheResolver(cpo.getCacheResolver());
				b.setCondition(cpo.getCondition());
				b.setKey(cpo.getKey());
				b.setKeyGenerator(cpo.getKeyGenerator());
				b.setName(cpo.getName());
				b.setUnless(cpo.getUnless());
				return b.build();
			}
			log.warn("unknown CacheOperation type cannot rewrite cacheManager property value，return primitive CacheOperation value:{}", operation);
			return operation;
		}
		
		@Override
		protected Object getCacheKey(Method method, Class<?> targetClass) {
			if(!finishedMethodMap.containsKey(method)) {
				primitiveMethodMap.put(AopUtils.getMostSpecificMethod(method, targetClass), method);
				finishedMethodMap.put(method, true);
			}
			return super.getCacheKey(method, targetClass);
		}

		private Map methodCacheMap = new HashMap();

		@Override
		@Nullable
		public Collection<CacheOperation> getCacheOperations(Method method, @Nullable Class<?> targetClass) {
			ThreadLocalHelper.set(FrameCommonConstants.REQUEST_CACHE_METHOD,method);
			if( FrameService.class.isAssignableFrom(targetClass) )
			{
				Object cacheKey = methodCacheMap.get(getCacheKey(method,targetClass));
				if( cacheKey == null )
				{

					Method method1 = getEqualsMethod(method,targetClass);
					if( method1 != null )
					{
						method = method1;
						ThreadLocalHelper.set("cacheTargetClass",targetClass);
					}

					Collection<CacheOperation> coll = super.getCacheOperations(method,targetClass);
					methodCacheMap.put(cacheKey,coll);
					ThreadLocalHelper.del("cacheTargetClass");
					return coll;
				}
				return (Collection<CacheOperation>)methodCacheMap.get(cacheKey);
			}
			return super.getCacheOperations(method,targetClass);
		}
	}
	private Method getEqualsMethod(Method method,Class<?> targetClass)
	{
		Method methods[] =  targetClass.getDeclaredMethods();
		Method method1 = getEqualsMethod(method,targetClass.getDeclaredMethods());
		if( method1 == null )
		{
			method1 = getEqualsMethod(method,targetClass.getMethods());
		}
		return method1;
	}

	private Method getEqualsMethod(Method method,Method methods[]) {
		for (Method method2 : methods) {
			if (methodEquals(method, method2)) {
				return method2;
			}
		}
		return null;
	}

	private boolean methodEquals(Method method,Method method2)
	{
		if( method.getName().equals(method2.getName()) )
		{
			Class paramTypes[] = method.getParameterTypes();
			Class paramTypes2[] = method2.getParameterTypes();
			if( paramTypes.length == paramTypes2.length )
			{
				for( int i=0;i<paramTypes.length;i++ )
				{
					if( !paramTypes[i].isAssignableFrom(paramTypes2[i]))
					{
						return false;
					}
				}
				return true;
			}
		}

		return false;
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.registerCacheManager(applicationContext);
	}

	private String getPath(FeignClient feignClient) {
		String path = feignClient.path();
		if(StringUtils.isBlank(path)) {
			return "";
		}
		if(path.startsWith(SystemPropertyUtils.PLACEHOLDER_PREFIX)) {
			String result = environment.resolvePlaceholders(path);
			return result != null ? result : "";
		}
		return path;
	}



}
