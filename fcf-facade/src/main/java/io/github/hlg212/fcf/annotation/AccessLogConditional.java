package  io.github.hlg212.fcf.annotation;

import io.github.hlg212.fcf.properties.AccessLogProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Inherited;


@ConditionalOnProperty(matchIfMissing = true,value = "enable",prefix = AccessLogProperties.PREFIX)
@Inherited
public @interface AccessLogConditional {
}
