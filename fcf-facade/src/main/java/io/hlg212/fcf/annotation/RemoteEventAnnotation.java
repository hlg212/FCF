package io.hlg212.fcf.annotation;

import java.lang.annotation.*;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2019-01-08 18:05
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RemoteEventAnnotation {

    // dest.topic 为真正的topic
    // 不填就默认为 def
    public String topic() default  "";

}
