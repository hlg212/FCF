package  io.github.hlg212.fcf.core.condition;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import  io.github.hlg212.fcf.core.conf.CacheConfig;

/**
 * 用于决定{@link CacheConfig} 是否加载
 *
 * @author  huangligui
 * @create: 2019-03-01 13:23
 */
public class CacheCondition implements Condition{

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return StringUtils.isNotBlank(context.getEnvironment().getProperty("spring.redis.host"));
	}
}
