package io.hlg212.fcf.annotation;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @description: 只读的缓存配置
 * @author: huangligui
 * @create: 2018-12-07 10:01
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Cacheable(unless = "true")
public @interface CacheableReadOnly {

	/**
	 * cacheName list
	 * @return
	 */
    @AliasFor(annotation = Cacheable.class)
    String[] value() default {};

    /**
     * 缓存key名称
     * @return
     */
    @AliasFor(annotation = Cacheable.class)
    String key() default "";

    /**
     * 缓存条件，SpEL表达式
     * @return
     */
    @AliasFor(annotation = Cacheable.class)
    String condition() default "";
}
