package com.hlg.fcf.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class BeanHelper {

    private static final String BEAN_INFO_FIELDNAMES = "BEAN_INFO_FIELDNAMES";

    public static Collection<String> getFieldNames(Class c) {
        String cacheId = BEAN_INFO_FIELDNAMES + c;

        Set result = CacheDataHelper.get(cacheId);
        if (result == null) {
            synchronized (cacheId) {
                result = CacheDataHelper.get(cacheId);
                if (result == null) {
                    result = new LinkedHashSet();
                    CacheDataHelper.put(cacheId, result);
                    Class sc = c.getSuperclass();
                    if (sc != null && sc != Object.class) {
                        result.addAll(getFieldNames(sc));
                    }
                    for (Field f : c.getDeclaredFields()) {
                        result.add(f.getName());
                    }
                }
                return result;
            }
        } else {
            return result;
        }

    }

}

