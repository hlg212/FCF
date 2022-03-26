package  io.github.hlg212.fcf.annotation;

import java.lang.annotation.*;

/**
 * @author  huanglg
 * @date 2019-09-09
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PoInfoAnn {

    public String desc() default  "";

    public String businessCode() default  "";

    public String attributeDetails() default  "";

}
