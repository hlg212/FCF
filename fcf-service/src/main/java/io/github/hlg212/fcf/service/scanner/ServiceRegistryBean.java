/** 
 * Project Name:demo 
 * File Name:HtcfServiceRegistryBean.java 
 * Package Name:com.htcf.proxy 
 * Date:2018年8月9日 下午2:25:23 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.service.scanner;

import  io.github.hlg212.fcf.env.DefaultPackagePropertySource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 代理service注册
 *
 * @author huangligui
 * @date 2020年8月18日
 */
@Component
public class ServiceRegistryBean implements BeanDefinitionRegistryPostProcessor {
	
	private BeanDefinitionRegistry beanDefinitionRegistry;


	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		ClassPathServiceProxyScanner beanDefinitionScanner = new ClassPathServiceProxyScanner(beanDefinitionRegistry,beanFactory);
		beanDefinitionScanner.registerFilters();
		List<String> basePackages = DefaultPackagePropertySource.getBasePackages();
		beanDefinitionScanner.doScan(StringUtils.toStringArray(basePackages));
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
		this.beanDefinitionRegistry = beanDefinitionRegistry;
	}

	
}
