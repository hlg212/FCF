
package io.hlg212.fcf.util;

import io.hlg212.fcf.Constants;
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
 * @author: huangligui
 * @create: 2020-03-30 16:01
 */
@Component(Constants.FRAME_BEAN_PREFIX + "SpringHelper")
public class SpringHelper implements ApplicationContextAware,BeanFactoryAware{
	
	private static ApplicationContext applicationContext;
	
	private static BeanFactory beanFactory;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringHelper.applicationContext = applicationContext;
	}

	public static void initApplicationContext(ApplicationContext applicationContext) {
		SpringHelper.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		SpringHelper.beanFactory = beanFactory;
	}
	
	public static BeanFactory getBeanFactory() {
		return SpringHelper.beanFactory;
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
				obj = SpringHelper.getBean(destClass);
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
			return (S) SpringHelper.getBean(beans[beans.length - 1]);
		}
		return (S) SpringHelper.getBean(beans[0]);
	}
}
