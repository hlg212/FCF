package com.hlg.fcf.core.cachex;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

import com.hlg.fcf.util.DbUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import com.hlg.fcf.constants.FrameCommonConstants;
import com.hlg.fcf.core.cache.PkIdCache;
import com.hlg.fcf.model.Model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(FrameCommonConstants.PK_ID_GENERATOR)
public class PkIdGenerator implements KeyGenerator {
	
	private PkIdCache pkIdCache = PkIdCache.getInstance();

	@Override
	public Object generate(Object target, Method method, Object... params) {
		if(params == null || params.length == 0 || !(params[0] instanceof Model)) {
			return null;
		}
		Model model = (Model) params[0];
		Class<? extends Model> clazz = model.getClass();
		String pkId = this.getPkId(clazz);
		if(StringUtils.isBlank(pkId)) {
			log.error("请手动用@PkId注解标注实体:{}对应的主键属性", clazz);
			return null;
		}
		try {
			Field pkIdField = FieldUtils.getField(clazz, pkId, true);
			Object pkIdValue = pkIdField.get(model);
			if(pkIdValue == null && pkIdField.getType().isAssignableFrom(String.class)) {
				pkIdValue = UUID.randomUUID().toString().replaceAll("-", "");
				pkIdField.set(model, pkIdValue);
			}
			if(pkIdValue == null) {
				log.warn("当前主键属性:{} 类型非String，请手动给其主键设值", pkId);
			}
			return pkIdValue;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.error("动态生成主键值出现异常", e);
		}
		return null;
	}

	private String getPkId(Class<? extends Model> clazz) {
		String pkId = pkIdCache.get(clazz.getName());
		if(pkId != null) {
			return pkId;
		}
		pkId = pkIdCache.get(clazz.getSuperclass().getName());
		if(pkId != null) {
			return pkId;
		}
		return DbUtils.getModelPkId(clazz);
	}
}
