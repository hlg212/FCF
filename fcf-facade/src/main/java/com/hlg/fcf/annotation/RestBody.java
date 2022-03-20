/**
 * 
 */
package com.hlg.fcf.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  用于设置rest服务接口，是否包裹框架统一返回值,可作用于方法体或类上。
 * @author changwei
 * @date 2018年9月30日
 */
@Target({ElementType.FIELD, ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestBody {
	
	/**
	 * 设置rest服务接口返回值是否包裹框架层统一对象，默认值为:true
	 * @return
	 */
	public boolean value() default true;
}
