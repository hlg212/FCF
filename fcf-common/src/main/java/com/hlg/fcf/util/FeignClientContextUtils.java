package com.hlg.fcf.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Iterator;
import java.util.Map;

/**
 *  动态生成feign接口
 *  一般是公共框架接口，每个服务的有，调用时确定具体服务
 *
 * @author huangligui
 * @date 2019年10月11日
 */
public class FeignClientContextUtils{


	/**
	 * 获取feign接口实例对象
	 * @param feignInterfaceClass feign接口class
	 * @param systemName feign接口对应系统contextPath值，如system项目contextPath为system，此项值框架的默认配置为系统名称取值为${spring.application.name}
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> feignInterfaceClass, String systemName) {
		if(StringUtils.isBlank(systemName)) {
			ExceptionHelper.throwServerException("获取feign接口实例对象时systemName不能为空!");
		}
		String beanName = systemName +  "_" + feignInterfaceClass.getSimpleName();
		Object bean = SpringUtils.getBean(beanName);
		if( bean == null ) {
			GenericApplicationContext c = (GenericApplicationContext) SpringUtils.getApplicationContext();
			final DefaultListableBeanFactory b = (DefaultListableBeanFactory) c.getBeanFactory();
			AbstractBeanDefinition abstractBeanDefinition = (AbstractBeanDefinition) c.getBeanDefinition( getCloneBeanName(c.getBeansOfType(feignInterfaceClass)));

			AbstractBeanDefinition clone = (AbstractBeanDefinition) abstractBeanDefinition.clone();
			MutablePropertyValues mutablePropertyValues = clone.getPropertyValues();
			mutablePropertyValues.removePropertyValue("path");
			mutablePropertyValues.addPropertyValue("path", systemName);

			b.registerBeanDefinition(beanName, clone);
			bean = SpringUtils.getBean(beanName);
		}
		return (T) bean;
	}

	private static String getCloneBeanName(Map map)
	{
        Iterator<Map.Entry> iter = map.entrySet().iterator();

        while( iter.hasNext()){
            Map.Entry entry = iter.next();
            if(entry.getValue().getClass().getSimpleName().startsWith("$Proxy")){
                return (String)entry.getKey();
            }
        }

		ExceptionHelper.throwServerException("找不到可以复制的接口!");
		return null;
	}
}
