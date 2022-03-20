package com.hlg.fcf.annotation;

import java.lang.annotation.*;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: wuwei
 * @create: 2019-09-09
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PobmsAnnotation {
    // 不填就默认为 def
    public String bms() default  "";

    public String ywbm() default  "";

    public String sxms() default  "";

}
