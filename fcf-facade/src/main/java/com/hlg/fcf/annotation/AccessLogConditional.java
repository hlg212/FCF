package com.hlg.fcf.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Inherited;


@ConditionalOnProperty(matchIfMissing = true,value = "log.access.enable",prefix = "hlg")
@Inherited
public @interface AccessLogConditional {
}
