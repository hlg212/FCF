/** 
 * Project Name:demo 
 * File Name:HtcfServiceJdkProxyFactory.java 
 * Package Name: io.github.hlg212.fcf.core.proxy
 * Date:2018年8月9日 下午2:23:54 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.service.proxy;

import  io.github.hlg212.fcf.annotation.DataSource;
import  io.github.hlg212.fcf.constants.FrameCommonConstants;
import  io.github.hlg212.fcf.service.FrameService;
import  io.github.hlg212.fcf.service.impl.ServiceWapper;
import  io.github.hlg212.fcf.util.ThreadLocalHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * service代理包装
 *
 * @author huangligui
 * @date 2020年8月18日
 */
@Slf4j
public class ServiceProxyFactory<T> implements FactoryBean<T> {

	private Class<T> interfaceClass;

	public Class<T> getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	private Object object = null;
	@Override
	public T getObject() throws Exception {
//		HtcfServiceJdkProxy<T> myProxy = new HtcfServiceJdkProxy<T>();
//		return (T) myProxy.bind(interfaceClass);
		if( object == null) {
			object = newInstance(interfaceClass);
		}

		return (T)object;
	}
	
	@Override
	public Class<?> getObjectType() {
		return interfaceClass;
	}

	private T newInstance(Class interfaceClass) {

		Enhancer enhancer = new Enhancer();
		//设置要创建动态代理的类
		enhancer.setInterfaces(new Class[]{interfaceClass});
		enhancer.setSuperclass(ServiceWapper.class);
		enhancer.setCallback(new ServiceWapperEnhancer(interfaceClass));

		return (T) enhancer.create();
	}


	class ServiceWapperEnhancer implements MethodInterceptor {

		private Class serviceClass;
		private Map<Method,Boolean> isInvokeSuperMethods = new HashMap();
		private Map<Method,Method> declaringMethods = new HashMap();
		private static final String METHOD_GETSERVICECLASS="getServiceClass";
		private static final String METHOD_GETWORKSERVICE="getWorkService";
		public ServiceWapperEnhancer(Class serviceClass) {
			this.serviceClass = serviceClass;
		}
		@Override
		public Object intercept(Object obj, Method method, Object[] args,
								MethodProxy proxy) throws Throwable {
			if (Object.class.equals(method.getDeclaringClass())) {
				return method.invoke(this, args);
			}
			if( METHOD_GETSERVICECLASS.endsWith(method.getName()) )
			{
				return serviceClass;
			}
			if( !METHOD_GETWORKSERVICE.endsWith(method.getName()) && obj instanceof ServiceWapper ) {
				Object workService = ((ServiceWapper) obj).getWorkService();
				if( workService != null )
				{
					setDataSource(AopUtils.getTargetClass(workService));
				}
				else
				{
					setDataSource(serviceClass);
				}
			}

			Class dClass = method.getDeclaringClass();
			if( dClass == ServiceWapper.class )
			{
				Boolean isInvokeSuper = isInvokeSuper(method);

				if( isInvokeSuper )
				{
					return proxy.invokeSuper(obj, args);
				}
			}
			if( obj instanceof ServiceWapper )
			{
				Object workService = ((ServiceWapper) obj).getWorkService();
				if( workService == null ) {
					return proxy.invokeSuper(obj, args);
				}
				Method dm = declaringMethods.get(method);
				if( dm != null )
				{
					return dm.invoke(workService,args);
				}
				//return method.invoke(((ServiceWapper) obj).getWorkService(),args);
				return proxy.invoke(workService, args);
			}
			return proxy.invoke(obj, args);
		}

		private void setDataSource(Class<?> sclass) {
			DataSource dataSource = sclass.getAnnotation(DataSource.class);
			if(dataSource != null) {
				log.debug("{}指定其动态数据源key为:{}",sclass, dataSource.value());
				ThreadLocalHelper.set(FrameCommonConstants.DYNAMIC_DATA_SOURCE_KEY, dataSource.value());
			}
			else{
				ThreadLocalHelper.del(FrameCommonConstants.DYNAMIC_DATA_SOURCE_KEY);
			}
		}

		private Boolean isInvokeSuper(Method method)
		{
			Boolean isInvokeSuper = isInvokeSuperMethods.get(method);
			if( isInvokeSuper == null ) {
				if( !FrameService.class.isAssignableFrom(serviceClass) )
				{
					try {
						Method dm = serviceClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
						if (dm != null) {
							isInvokeSuper = false;
							declaringMethods.put(method,dm);
						}
					} catch (NoSuchMethodException e) {
						//e.printStackTrace();
						isInvokeSuper = true;
					}
				}
				else
				{
					isInvokeSuper = true;
				}
				isInvokeSuperMethods.put(method,isInvokeSuper);
			}
			return isInvokeSuper;
		}

	}

}
