/** 
 * Project Name:frame-common 
 * File Name:Fileds.java 
 * Package Name: io.github.hlg212.fcf.annotation
 * Date:2018年8月16日 下午5:12:06 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * 作用于实体类上，用于接收定义@Field定义的数组集 </br>
 * 
 * @date 2018年8月16日
 * @author huangligui 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Fields {

	public Field[] value();
}
