package io.hlg212.fcf.web.annotation;

import java.lang.annotation.Inherited;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

@ConditionalOnClass(
        name = {"org.springframework.web.servlet.DispatcherServlet"}
)
@Inherited
public @interface MvcConditional {
}
