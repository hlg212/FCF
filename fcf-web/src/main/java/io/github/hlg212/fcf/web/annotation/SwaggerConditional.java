package  io.github.hlg212.fcf.web.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Inherited;

@MvcConditional
@ConditionalOnProperty(matchIfMissing = true,value = "swagger.enable",prefix = "fcf")
@Inherited
public @interface SwaggerConditional {
}
