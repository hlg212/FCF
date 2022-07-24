package io.github.hlg212.fcf.annotation;

import io.github.hlg212.fcf.properties.PrefixConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Inherited;

@ConditionalOnProperty(prefix = PrefixConstants.DATA_AUTHORITY_PROPERTIES_PREFIX, name = "enable", havingValue = "true")
@Inherited
public @interface DataAuthorityConditional {

}
