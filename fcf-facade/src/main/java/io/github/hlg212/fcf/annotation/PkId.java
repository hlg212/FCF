/** 
 * Project Name:frame-core 
 * File Name:Id.java 
 * Package Name: io.github.hlg212.fcf.core.annotation
 * Date:2018年8月7日 上午11:05:29 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户标志实体主键属性.
 *
 * @author huangligui
 * @date 2018年8月7日
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PkId {

}
