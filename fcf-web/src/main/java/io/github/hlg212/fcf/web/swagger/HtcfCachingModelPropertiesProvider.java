/** 
 * Project Name:demo 
 * File Name:HtcfCachingModelPropertiesProvider.java 
 * Package Name:com.htcf.service 
 * Date:2018年8月15日 下午3:13:36 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.web.swagger;

import java.util.List;

import  io.github.hlg212.fcf.web.annotation.MvcConditional;
import  io.github.hlg212.fcf.web.annotation.SwaggerConditional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.schema.ModelProperty;
import springfox.documentation.schema.property.CachingModelPropertiesProvider;
import springfox.documentation.schema.property.ModelPropertiesProvider;
import springfox.documentation.spi.schema.contexts.ModelContext;

/** 
 * 
 * 2018年8月15日
 * 
 * @author huangligui 
 */
@Primary
@Component
@Qualifier("cachedModelProperties")
@SwaggerConditional
public class HtcfCachingModelPropertiesProvider extends CachingModelPropertiesProvider {

	/** 
	 * Creates a new instance of HtcfCachingModelPropertiesProvider. 
	 * 
	 * @param resolver
	 * @param delegate 
	 */
	@Autowired
	public HtcfCachingModelPropertiesProvider(TypeResolver resolver,  @Qualifier("htcfOptimized")ModelPropertiesProvider delegate) {
		super(resolver, delegate);
	}
	
	@Override
	public List<ModelProperty> propertiesFor(ResolvedType type, ModelContext givenContext) {
		return super.propertiesFor(type, givenContext);
	}

}
