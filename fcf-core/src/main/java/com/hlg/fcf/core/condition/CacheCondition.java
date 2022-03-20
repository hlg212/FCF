package com.hlg.fcf.core.condition;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.hlg.fcf.core.conf.CacheConfig;

/**
 * 用于决定{@link CacheConfig} 是否加载
 * 
 * @author changwei
 * @date 2018年12月7日
 */
public class CacheCondition implements Condition{

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return StringUtils.isNotBlank(context.getEnvironment().getProperty("spring.redis.host"));
	}
}
