/**
 * 
 */
package com.hlg.fcf.core.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 
 * @author changwei
 * @date 2018年10月22日
 */
public class DataSourceCondition implements Condition{

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return "true".equals(context.getEnvironment().getProperty("hlg.enable-data-source"));
	}

}
