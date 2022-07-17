package  io.github.hlg212.fcf.web.annotation;

import io.github.hlg212.fcf.web.properties.SwaggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Inherited;

@MvcConditional
@ConditionalOnProperty(matchIfMissing = true,value = "enable",prefix = SwaggerProperties.PREFIX)
@Inherited
public @interface SwaggerConditional {
}
