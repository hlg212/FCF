package io.hlg212.fcf.annotation;

import java.lang.annotation.*;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: wuwei
 * @create: 2019-07-03
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TaskRegister {
    // 不填就默认为 def
    public String name() default  "";

    public String beanName() default  "";

    public String cron();

    public String param() default  "";

    public boolean autoStart() default  true;

}
