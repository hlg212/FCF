/**
 * 
 */
package com.hlg.fcf.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//import org.springframework.context.annotation.Import;

//import com.hlg.fcf.web.conf.SpringWebConfig;

/**
 * spring @RequestParam与@RequestBody的综合体,作用于controller方法参数上.一般用于自定义对象不建议作用在普通的类型上
 * @author changwei
 * @date 2018年10月24日
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Import(SpringWebConfig.class)
public @interface RequestParamOrBody {

}
