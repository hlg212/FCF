package io.hlg212.fcf.core.interceptor;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import io.hlg212.fcf.constants.FrameCommonConstants;
import io.hlg212.fcf.util.ThreadLocalHelper;

/**
 * 用于记录存储cache注解作用的method对象
 * @author: huangligui
 * @date 2018年12月20日
 */
@Aspect
@Component
public class CacheMethodInterceptor implements Ordered{

	@Pointcut("@annotation(io.hlg212.fcf.annotation.CacheRemove) "
			+ "|| @annotation(org.springframework.cache.annotation.Cacheable)"
			+ "|| @annotation(org.springframework.cache.annotation.CacheEvict) "
			+ "|| @annotation(org.springframework.cache.annotation.CachePut)"
			+ "|| @annotation(io.hlg212.fcf.annotation.CacheableReadOnly)")
	public void cacheMethod() {
	}
	
	@Before(value="cacheMethod()")
	public void before(JoinPoint joinPoint){
		MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		ThreadLocalHelper.set(FrameCommonConstants.REQUEST_CACHE_METHOD, method);
	}
	
	@After(value="cacheMethod()")
	public void after(JoinPoint joinPoint) {
		ThreadLocalHelper.del(FrameCommonConstants.REQUEST_CACHE_METHOD);
	}
	
	@Override
	public int getOrder() {
		return 100;
	}
}
