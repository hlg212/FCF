/** 
 * Project Name:demo 
 * File Name:ClassPathServiceScanner.java 
 * Package Name:com.htcf.proxy 
 * Date:2018年8月13日 下午1:39:43 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.service.scanner;

import  io.github.hlg212.fcf.service.impl.Constants;
import  io.github.hlg212.fcf.service.proxy.ServiceProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 代理service扫描
 *
 * @author huangligui
 * @date 2020年8月18日
 */
@Slf4j
public class ClassPathServiceProxyScanner extends ClassPathBeanDefinitionScanner {

	private ConfigurableListableBeanFactory beanFactory;
	/**
	 * Creates a new instance of ClassPathServiceScanner.
	 *
	 * @param registry
	 */
	public ClassPathServiceProxyScanner(BeanDefinitionRegistry registry,ConfigurableListableBeanFactory beanFactory) {
		super(registry);
		this.beanFactory = beanFactory;
	}
	
	
	public void registerFilters() {
		String reg = ".+Service$";
		Pattern pattern = Pattern.compile(reg);
		addIncludeFilter(new RegexPatternTypeFilter(pattern ));
	}
	
	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		log.info("scan service path:{}", Arrays.toString(basePackages));
		return super.doScan(basePackages);
	}
	
	@Override
	protected void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry) {
		Class serviceClass = null;
		try {
			serviceClass =  Class.forName(definitionHolder.getBeanDefinition().getBeanClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String names[] = beanFactory.getBeanNamesForType(serviceClass);
		/*Object o = beanFactory.getBean(serviceClass);
		if( o != null )
		{
			return;
		}*/
//		if( names.length > 0 )
//		{
//			return;
//		}
		//beanFactory.is
		AbstractBeanDefinition beanDefinition =  (AbstractBeanDefinition) definitionHolder.getBeanDefinition();
		String realInterfaceName = beanDefinition.getBeanClassName();
		beanDefinition.getPropertyValues().add("interfaceClass", beanDefinition.getBeanClassName());
		beanDefinition.setBeanClass(ServiceProxyFactory.class);
		beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
		beanDefinition.setPrimary(true);
		String beanName = definitionHolder.getBeanName() + Constants.SERVICEPROXY_SUFFIX;
		registry.registerBeanDefinition(beanName, beanDefinition);
		log.info("register proxy interface instance:{},", realInterfaceName);
	}
	
	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		AnnotationMetadata metadata = beanDefinition.getMetadata();
		try {
			Class<?> c = Class.forName(metadata.getClassName());
			return c.isInterface();
//			return c.isInterface() && HtcfService.class.isAssignableFrom(c)
//					&& !HtcfService.class.toString().equals(c.toString())
//					&& !QueryService.class.toString().equals(c.toString())
//					&& !CurdService.class.toString().equals(c.toString());
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	
}
