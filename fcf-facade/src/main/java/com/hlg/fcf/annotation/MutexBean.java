package com.hlg.fcf.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

import com.hlg.fcf.condition.MutexBeanCondition;

/**
 *  互斥bean, 根据key互斥只允许存在一个bean创建
 * @author changwei
 * @date 2019年1月15日
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(MutexBeanCondition.class)
public @interface MutexBean {
	
	public String key();
}
