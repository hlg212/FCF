package  io.github.hlg212.fcf.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Inherited;

@ConditionalOnProperty(prefix = "hlg.dao.authority", name = "enable", havingValue = "true")
@Inherited
public @interface DataAuthorityConditional {
}
