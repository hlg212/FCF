package io.github.hlg212.fcf.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CollectionHelper {

    public static Collection getPropertyValues(Collection collection,String proName) throws InstantiationException,IllegalAccessException
    {
        Collection result = collection.getClass().newInstance();

        return getPropertyValues(collection,proName,result);

    }

    public static Collection getPropertyValues(Collection collection,String proName,Collection result)
    {
        for( Object obj : collection )
        {
            try {
                Object val = PropertyUtils.getProperty(obj,proName);
                if( val != null )
                {
                    result.add(val);
                }
            } catch (Exception e) {
                log.warn(e.getMessage(),e);
            }
        }
        return result;
    }

    /**
     * 将集合转成map,采用某个属性作为key
     * value还是为集合对象；
     *
     * @param collection 列表集合
     * @param proName  属性名称
     * @return 默认HashMap;
     */
    public static Map getPropertyMap(Collection collection, String proName)
    {
        Map  resultMap = new HashMap();

        return getPropertyMap(collection,proName,resultMap);
    }

    public static Map getPropertyMap(Collection collection, String proName,Map resultMap)
    {
        for( Object obj : collection )
        {
            try {
                Object val = PropertyUtils.getProperty(obj,proName);
                if( val != null )
                {
                    resultMap.put(val,obj);
                }
            } catch (Exception e) {
                log.warn(e.getMessage(),e);
            }
        }
        return resultMap;
    }

}
