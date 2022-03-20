/**
 * 
 */
package com.hlg.fcf.core.interceptor;

import com.hlg.fcf.annotation.CacheRemove;
import com.hlg.fcf.core.cachex.ExCacheErrorHandler;
import com.hlg.fcf.core.cachex.NoneCache;
import com.hlg.fcf.model.Model;
import com.hlg.fcf.service.CurdService;
import com.hlg.fcf.util.ParameterizedTypeHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 自定义{@link CacheRemove} 拦截器实现
 * @author changwei
 * @date 2018年11月20日
 */
@Slf4j
@Aspect
@Component
@ConditionalOnBean(com.hlg.fcf.core.conf.CacheConfig.class)
public class CacheRemoveInterceptor implements Ordered{
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private BeanFactory beanFactory;
	
	@Autowired
	private ExCacheErrorHandler cacheErrorHandler;

	
	private BeanFactoryResolver beanFactoryResolver ;
	
	private final SpelExpressionParser expressionParser = new SpelExpressionParser();
	
	private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
	
	
	@PostConstruct
	public void init() {
		beanFactoryResolver = new BeanFactoryResolver(beanFactory);
	}

	@Pointcut("@annotation(com.hlg.fcf.annotation.CacheRemove)")
	public void method() {
		
	}
	
	@Around("method()")
	public void handler(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Object target = joinPoint.getTarget();
		CacheRemove cacheRemove = method.getAnnotation(CacheRemove.class);
		
		this.processCacheRemove(cacheRemove, cacheRemove.beforeInvocation(), method, joinPoint.getArgs(), target);
		
		joinPoint.proceed();
		
		this.processCacheRemove(cacheRemove, !cacheRemove.beforeInvocation(), method, joinPoint.getArgs(), target);
	}
	
	private void processCacheRemove(CacheRemove cacheRemove, boolean beforeInvocation, Method method, Object[] args, Object target) {
		try {
			if(!beforeInvocation) {
				return ;
			}
			MethodBasedEvaluationContext basedEvaluationContext = new MethodBasedEvaluationContext(null, method, args, parameterNameDiscoverer);
			basedEvaluationContext.setBeanResolver(beanFactoryResolver);
			Object key = this.getExpression(cacheRemove.key()).getValue(basedEvaluationContext);
			if(key == null) {
				return ;
			}
			if(key.getClass().isArray()) {
				Object[] arr = (Object[]) key;
				for(Object o : arr) {
					this.del(o, this.getCacheNames(cacheRemove, target));
				}
			} else {
				this.del(key, this.getCacheNames(cacheRemove, target));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private String[] getCacheNames(CacheRemove cacheRemove, Object target){
		if(cacheRemove.cacheNames() != null && cacheRemove.cacheNames().length > 0) {
			return cacheRemove.cacheNames();
		}
		Class<?> targetClass = AopUtils.getTargetClass(target);
		CacheConfig cacheConfig = targetClass.getAnnotation(CacheConfig.class);
		String[] cacheNamesArr = cacheConfig == null ? null : cacheConfig.cacheNames();
		if(cacheNamesArr != null && cacheNamesArr.length > 0) {
			return cacheNamesArr;
		}
		if( !CurdService.class.isAssignableFrom(targetClass)) {
			log.warn("{}当前service未启动缓存", targetClass);
			return new String[] { NoneCache.CACHE_NAME};
		}
		Class modelClass = ParameterizedTypeHelper.getParameterizedType(targetClass,Model.class);
		return new String[] {modelClass.getSimpleName()};
	}
	
	private Collection<? extends Cache> getCache(String... cacheNames){
		Collection<Cache> result = new ArrayList<>();
		if(cacheNames == null) {
			return result;
		}
		for(String cacheName : cacheNames) {
			Cache cache = cacheManager.getCache(cacheName);
			result.add(cache);
		}
		return result;
	}
	
	protected void doEvict(Cache cache, Object key) {
		try {
			cache.evict(key);
		}
		catch (RuntimeException ex) {
			cacheErrorHandler.handleCacheEvictError(ex, cache, key);
		}
	}
	
	
	protected Expression getExpression(String expression) {
		return getParser().parseExpression(expression);
	}

	private void del(Object key,String[] cacheNames) {
		Collection<? extends Cache> caches = this.getCache(cacheNames);
		for(Cache cache : caches) {
			doEvict(cache, key);
		}
	}
	
	private SpelExpressionParser getParser() {
		return expressionParser;
	}

	@Override
	public int getOrder() {
		return 100+1;
	}
}
