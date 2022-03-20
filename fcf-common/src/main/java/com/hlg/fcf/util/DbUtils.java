/** 
 * Project Name:common-core 
 * File Name:DBUtil.java 
 * Package Name:com.hlg.fcf.core.dbRelevant 
 * Date:2016-12-27 16:36:35 
 * Copyright (c) 2016, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package com.hlg.fcf.util;

import com.hlg.fcf.annotation.PkId;
import com.hlg.fcf.annotation.Table;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  数据库实体映射工具类
 *
 *
 * @author changwei
 * @date 2018年9月20日
 */
public class DbUtils {

	/**
	 *
	 * 获取实体对应的表名
	 *
	 * @param clazz
	 * @return
	 */
	public static String getTableName(Class<?> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		String tableName = table == null ? null : table.value();
		if(StringUtils.isNotBlank(tableName)) {
			return tableName;
		}
		StringBuffer sb = new StringBuffer("T");
		String className = clazz.getSimpleName();
		for(int i = 0; i < className.length(); i ++) {
			char c = className.charAt(i);
			if(Character.isUpperCase(c)) {
				sb.append("_");
			}
			sb.append(Character.toUpperCase(c));
		}
		return sb.toString();
	}
	
	/**
	 * 获取实体中@PkId注解标识列
	 * @param clazz
	 * @return
	 */
	public static String getModelPkId(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			PkId id = field.getAnnotation(PkId.class);
			if(id != null) {
				return field.getName();
			}
		}
		for (Class<?> acls = clazz.getSuperclass(); acls != null; acls = acls.getSuperclass()) {
			String id =  getModelPkId(acls);
			if(id != null) {
				return id;
			}
        }
		return null;
	}
	

	
	/**
	 *  获取实体@Field注解as别名所映射关系
	 * @param clazz
	 * @return key: as别名 </br>
	 *         value: 属性名
	 */
	public static Map<String, String> getFieldAsMapping(Class<?> clazz) {
		Map<String, String> result = new HashMap<>();
		Set<Field> fileds = FieldHelper.getAllDeclaredField(clazz);
		for(Field field : fileds) {
			com.hlg.fcf.annotation.Field htcfField = field.getAnnotation(com.hlg.fcf.annotation.Field.class);
			if(htcfField == null) {
				continue;
			}
			String asFieldName = htcfField.as();
			if(StringUtils.isNotBlank(asFieldName)) {
				result.put(asFieldName, field.getName());
			}
		}
		return result;
	}

	
}
