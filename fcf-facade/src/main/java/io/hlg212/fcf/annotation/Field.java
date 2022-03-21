/** 
 * Project Name:frame-core 
 * File Name:Id.java 
 * Package Name:io.hlg212.fcf.core.annotation
 * Date:2018年8月6日 下午4:58:40 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package io.hlg212.fcf.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 *  作用于实体属性上，用于标注实体属性的一些映射关系，如字段中文描述、对应数据库中的列名等
 * 
 * @date 2018年8月6日
 * @author huangligui
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Field {
	
	/**
	 * 
	 * 实体字段名
	 * 
	 * @return
	 */
	public String name() default "";
	
	/**
	 * 
	 * 实体字段的中文描述
	 * 
	 * @return
	 */
	public String description();

	/**
	 * 
	 * 实体字段对应的数据库列名
	 * 
	 * @return
	 */
	public String columnName() default "";

	/**
	 *
	 *  数据表别名，出现多个同名时使用
	 *
	 * @return
	 */
	public String prefix() default "";

	/**
	 *  属性对应别名
	 * @return
	 */
	@Deprecated
	public String as() default "";
	
	
	public Class<?> typeHandler() default Void.class;
	
}
