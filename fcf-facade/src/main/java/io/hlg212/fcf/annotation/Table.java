/** 
 * Project Name:frame-core 
 * File Name:Table.java 
 * Package Name:io.hlg212.fcf.core.annotation
 * Date:2018年8月6日 下午4:35:07 
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
 *  用于指定实体对应的表名
 * 
 * @author huangligui
 * @date 2018年8月7日
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

	/**
	 * 
	 * 表名
	 * 
	 * @return
	 */
	public String value() ;
}
