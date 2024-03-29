package  io.github.hlg212.fcf.annotation;

import java.lang.annotation.*;

/**
 * @author  huangligui
 * @date 2019-01-08 18:05
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
