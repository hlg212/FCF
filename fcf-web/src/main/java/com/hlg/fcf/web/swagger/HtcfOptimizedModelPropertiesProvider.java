/** 
 * Project Name:demo 
 * File Name:HtcfOptimizedModelPropertiesProvider.java 
 * Package Name:com.htcf.service 
 * Date:2018年8月15日 下午3:17:44 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package com.hlg.fcf.web.swagger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hlg.fcf.web.annotation.MvcConditional;
import com.hlg.fcf.web.annotation.SwaggerConditional;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.types.ResolvedObjectType;
import com.hlg.fcf.annotation.Fields;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.schema.ModelProperty;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.schema.plugins.SchemaPluginsManager;
import springfox.documentation.schema.property.BeanPropertyNamingStrategy;
import springfox.documentation.schema.property.FactoryMethodProvider;
import springfox.documentation.schema.property.OptimizedModelPropertiesProvider;
import springfox.documentation.schema.property.bean.AccessorsProvider;
import springfox.documentation.schema.property.field.FieldProvider;
import springfox.documentation.spi.schema.contexts.ModelContext;

/** 
 * 
 * 2018年8月15日
 * 
 * @author changwei 
 */
@Slf4j
@Component("htcfOptimized")
@SwaggerConditional
public class HtcfOptimizedModelPropertiesProvider extends OptimizedModelPropertiesProvider {

	/** 
	 * Creates a new instance of HtcfOptimizedModelPropertiesProvider. 
	 * 
	 * @param accessors
	 * @param fields
	 * @param factoryMethods
	 * @param typeResolver
	 * @param namingStrategy
	 * @param schemaPluginsManager
	 * @param typeNameExtractor 
	 */
	@Autowired
	public HtcfOptimizedModelPropertiesProvider(AccessorsProvider accessors, FieldProvider fields,
			FactoryMethodProvider factoryMethods, TypeResolver typeResolver, BeanPropertyNamingStrategy namingStrategy,
			SchemaPluginsManager schemaPluginsManager, TypeNameExtractor typeNameExtractor) {

		super(accessors, fields, factoryMethods, typeResolver, namingStrategy, schemaPluginsManager, typeNameExtractor);
	}
	
	@Override
	public List<ModelProperty> propertiesFor(ResolvedType type, ModelContext givenContext) {
		List<ModelProperty> list = super.propertiesFor(type, givenContext);
		if(list == null || list.isEmpty()) {
			return list;
		}
		Class<?> curModelClass = null;
		try {
			curModelClass = this.getModelClass(givenContext);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		Map<String, String> fieldDescriptionMap = this.getModelFieldsDes(curModelClass);
		for(ModelProperty modelProperty : list) {
			try {
				Class<? extends ModelProperty> c = modelProperty.getClass();
				Field description = c.getDeclaredField("description");
				String curModelFieldName = modelProperty.getName();
				Field curModelField = FieldUtils.getField(curModelClass, curModelFieldName, true);
				if(curModelField == null) {
					continue;
				}
				com.hlg.fcf.annotation.Field htcfField = curModelField.getAnnotation(com.hlg.fcf.annotation.Field.class);
				if(htcfField == null && StringUtils.isEmpty(fieldDescriptionMap.get(curModelFieldName))) {
					continue;
				}
				Class<?>[] parameterTypes = new Class<?>[0];
				Method method = c.getDeclaredMethod("getDescription", parameterTypes);
				Object[] args = new Object[0];
				String apiModelPropertyDescription = (String) method.invoke(modelProperty, args);
				String classFieldDescription = fieldDescriptionMap.get(curModelFieldName);
				if(StringUtils.isNotEmpty(classFieldDescription)) {
					apiModelPropertyDescription = classFieldDescription;
				}
				description.setAccessible(true);
				description.set(modelProperty, htcfField == null || StringUtils.isEmpty(htcfField.description()) ? apiModelPropertyDescription : htcfField.description());
			} catch (NoSuchFieldException e) {
				//意料之中的异常，不做任何处理
				continue;
			}  catch (Exception e) {
				log.error("赋值swagger model description字段出现异常!", e);
			}
		}
		return list;
	}
	
	private Class<?> getModelClass(ModelContext givenContext) throws ClassNotFoundException{
		Type type = givenContext.getType();
		if(type instanceof ResolvedObjectType) {
			return ((ResolvedObjectType)type).getErasedType();
		}
		return Class.forName(type.toString());
	}
	
	private Map<String, String> getModelFieldsDes(Class<?> curModelClass){
		Map<String,String> result = new HashMap<>(10);
		Fields fields = curModelClass.getAnnotation(Fields.class);
		if(fields == null || ArrayUtils.isEmpty(fields.value())) {
			return result;
		}
		for(com.hlg.fcf.annotation.Field field : fields.value()) {
			result.put(field.name(), field.description());
		}
		return result;
	}

}
