package io.hlg212.fcf.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(CloudApplicationRegistrar.class)
@EnableFeignClients
//@EnableEurekaClient
@ComponentScan
public @interface CloudApplicationScan {
}
