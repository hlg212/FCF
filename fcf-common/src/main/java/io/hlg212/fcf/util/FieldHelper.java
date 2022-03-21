package io.hlg212.fcf.util;

import io.hlg212.fcf.cache.Constants;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
@CacheConfig(cacheNames = "Frame.field",cacheManager = Constants.CacheManager.SimpleCacheManager)
public class FieldHelper {

    private static FieldHelper _instance = null;
    public static FieldHelper getInstance()
    {
        if( _instance == null )
        {
            _instance = SpringHelper.getBean(FieldHelper.class);
        }
        return _instance;
    }

    public static Set<Field> getAllDeclaredField(Class<?> clazz){
        return getInstance().getAllField(clazz);
    }

    @Cacheable(key = "#p0+ '_(desc)'+ #p1")
    public Map<String,String> _getAllDeclaredFieldDesc(Class<?> clazz,boolean ignoreNull){
        Map<String, String> result = new HashMap<>();
		Set<Field> fileds = FieldHelper.getAllDeclaredField(clazz);
		for(Field field : fileds) {
			io.hlg212.fcf.annotation.Field htcfField = field.getAnnotation(io.hlg212.fcf.annotation.Field.class);
			if(htcfField == null) {
				continue;
			}
			String asFieldName = htcfField.description();
			if(org.apache.commons.lang.StringUtils.isNotBlank(asFieldName)) {
				result.put(field.getName(), asFieldName);
			}
			else if( !ignoreNull )
            {
                result.put(field.getName(), field.getName());
            }
		}
		return result;
    }

    public static Map<String,String> getAllDeclaredFieldDesc(Class<?> clazz,boolean ignoreNull){
        return getInstance()._getAllDeclaredFieldDesc(clazz,ignoreNull);
    }
    public static Map<String,String> getAllDeclaredFieldDesc(Class<?> clazz){
        return getAllDeclaredFieldDesc(clazz,true);
    }


    /**
     * 获取指定属性名称对应类型
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Class<?> getFieldType(Class<?> clazz, String fieldName) {
        Field field = getInstance()._getField(clazz,fieldName);
        if(field != null) {
            return field.getType();
        }
        return null;
    }

    @Cacheable(key = "#p0+ '_'+ #p1")
    public Field _getField(Class<?> clazz,String fieldName){
        Field field = FieldUtils.getField(clazz, fieldName, true);
        return field;
    }


    /**
     * 获取指定属性名称对应类型
     * @param clazz
     * @param fieldName
     * @return
     */
    public static io.hlg212.fcf.annotation.Field getFieldAnnotation(Class<?> clazz, String fieldName) {
        return getInstance()._getFieldAnnotation(clazz,fieldName);
    }


    @Cacheable(key = "#p0+ '_(anno)'+ #p1")
    public io.hlg212.fcf.annotation.Field _getFieldAnnotation(Class<?> clazz,String fieldName){
        Field field = getInstance()._getField(clazz,fieldName);
        return field.getDeclaredAnnotation(io.hlg212.fcf.annotation.Field.class);
    }

    @Cacheable(key = "#p0")
    public Set<Field> getAllField(Class<?> clazz){
        Set<Field> result = new HashSet<>();
        for (Class<?> acls = clazz; acls != null; acls = acls.getSuperclass()) {
            result.addAll(Arrays.asList(acls.getDeclaredFields()));
        }
        return result;
    }





}
