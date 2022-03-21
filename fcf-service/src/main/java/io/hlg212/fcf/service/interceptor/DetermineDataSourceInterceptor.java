package io.hlg212.fcf.service.interceptor;

import io.hlg212.fcf.annotation.DataSource;
import io.hlg212.fcf.constants.FrameCommonConstants;
import io.hlg212.fcf.service.impl.ServiceWapper;
import io.hlg212.fcf.util.ThreadLocalHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 用于动态指定数据源
 */
@Slf4j
@Aspect
@Component
@Order(100-1)
public class DetermineDataSourceInterceptor {

	@Pointcut("@annotation(io.hlg212.fcf.annotation.DataSource) || @annotation(org.springframework.transaction.annotation.Transactional)")
	public void serviceMethod(){
		
	}
	
	@Before(value="serviceMethod()")
	public void before(JoinPoint joinPoint){
		DataSource dataSource = getDataSource(joinPoint);
		if( dataSource != null )
		{
			ThreadLocalHelper.set(FrameCommonConstants.DYNAMIC_DATA_SOURCE_KEY, dataSource.value());
		}

	}

	private DataSource getDataSource(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Class<?> serviceImplClass = null;

		if (joinPoint.getTarget() instanceof ServiceWapper) {
			ServiceWapper serviceWapper = (ServiceWapper) joinPoint.getTarget();

			Object service = serviceWapper.getWorkService();
			if (service != null) {
				serviceImplClass = AopUtils.getTargetClass(service);
			}
			else
			{
				serviceImplClass = serviceWapper.getServiceClass();
			}
		} else {
			serviceImplClass = AopUtils.getTargetClass(joinPoint.getTarget());
		}


		DataSource dataSource = null;
		if( serviceImplClass != null ) {
			if (!serviceImplClass.equals(method.getDeclaringClass())) {
				try {
					method = serviceImplClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
					dataSource = method.getAnnotation(DataSource.class);
					if (dataSource != null) {
						log.debug("{}.{}动态切换数据源至:{}", serviceImplClass, method.getName(), dataSource.value());
					}
				} catch (NoSuchMethodException | SecurityException e) {

				}
			}
			else
			{
				dataSource = method.getAnnotation(DataSource.class);
				if( dataSource != null ) {
					log.debug("{}.{}动态切换数据源至:{}", serviceImplClass, method.getName(), dataSource.value());
				}
			}
			if (dataSource == null) {
				dataSource = serviceImplClass.getAnnotation(DataSource.class);
			}
		}

		return dataSource;
	}
	
	@After(value="serviceMethod()")
	public void after(JoinPoint joinPoint){
		ThreadLocalHelper.del(FrameCommonConstants.DYNAMIC_DATA_SOURCE_KEY);
	}
	
	
}
