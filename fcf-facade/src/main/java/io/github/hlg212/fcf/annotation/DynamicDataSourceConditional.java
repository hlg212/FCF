package io.github.hlg212.fcf.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Inherited;


@ConditionalOnProperty(havingValue="true",value = "enable-data-source",prefix = "fcf")
@Inherited
public @interface DynamicDataSourceConditional {
}
