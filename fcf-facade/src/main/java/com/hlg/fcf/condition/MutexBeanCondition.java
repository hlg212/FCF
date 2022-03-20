package com.hlg.fcf.condition;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.hlg.fcf.annotation.MutexBean;

/**
 * 
 * @author changwei
 * @date 2019年1月14日
 */
public class MutexBeanCondition implements Condition{
	
	private static ThreadLocal<Map<String, Boolean>> local = new ThreadLocal<>();

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String annotationName = MutexBean.class.getName();
		if(!metadata.isAnnotated(annotationName)) {
			return true;
		}
		Map<String, Object> attributes = metadata.getAnnotationAttributes(annotationName);
		String key = String.valueOf(attributes.get("key"));
		boolean result = Optional.ofNullable(local.get()).orElse(Collections.emptyMap()).get(key) == null;
		Map<String, Boolean>  map = new HashMap<>(1);
		map.put(key, true);
		local.set(map);
		return result;
	}
	
}
