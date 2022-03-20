/** 
 * Project Name:demo 
 * File Name:SpringUtils.java 
 * Package Name:com.hlg.fcf.core.util 
 * Date:2018年8月9日 下午5:37:20 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package com.hlg.fcf.util;

import com.hlg.fcf.Constants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/** 
 * 用于获取spring上下文对象
 * 
 * date: 2018年8月14日
 * 
 * @author changwei
 */
@Component(Constants.FRAME_BEAN_PREFIX + "SpringUtils")
public class SpringUtils implements ApplicationContextAware,BeanFactoryAware{
	
	private static ApplicationContext applicationContext;
	
	private static BeanFactory beanFactory;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.applicationContext = applicationContext;
	}

	public static void initApplicationContext(ApplicationContext applicationContext) {
		SpringUtils.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		SpringUtils.beanFactory = beanFactory;
	}
	
	public static BeanFactory getBeanFactory() {
		return SpringUtils.beanFactory;
	}

	public static Object getBean(String beanName)
	{
		try{
			return applicationContext.getBean(beanName);
		}catch (Exception e)
		{
			return null;
		}

	}

	public static <T> T getBean(Class<T> cls)
	{
		try {
			return applicationContext.getBean(cls);
		}catch (Exception e)
		{
			return null;
		}
	}

	private static String generateCacheId(Class srcClass,Class destClass)
	{
		String cacheId = srcClass.getName() + ":" + destClass.getName();
		return cacheId;
	}

	public static <S extends Object> S getBeanByResolvableType(Class srcClass,Class destClass)
	{
		String cacheId = generateCacheId(srcClass,destClass);
		Object obj = CacheDataHelper.get(cacheId);
		if( obj == null ) {
			if( destClass.getTypeParameters().length == 0 ) {
				obj = SpringUtils.getBean(destClass);
			}
			else {
				Class bc = ParameterizedTypeHelper.getParameterizedTypeIndex0(srcClass);

				//Class bc = ResolvableType.forClass(srcClass).getInterfaces()[0].getGeneric(0).resolve();

				ResolvableType type = ResolvableType.forClassWithGenerics(destClass, bc);

				obj = getBeanByResolvableType(type);
			}
			CacheDataHelper.put(cacheId,obj);
		}
		return (S)obj;
	}

	public static <S extends Object> S getBeanByResolvableType(ResolvableType type)
	{
		String beans[] = getApplicationContext().getBeanNamesForType(type);

		if(beans.length == 0)
		{
			return null;
		}
		if( beans.length > 1 )
		{
			return (S) SpringUtils.getBean(beans[beans.length - 1]);
		}
		return (S) SpringUtils.getBean(beans[0]);
	}
}
