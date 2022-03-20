package com.hlg.fcf.core.dao.impl;

import com.hlg.fcf.core.properties.AutoFillFieldProperties;
import com.hlg.fcf.dao.FillHandler;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Set;

public class CreateTimeFillHandler implements FillHandler {

    @Autowired
    private AutoFillFieldProperties autoFillFieldProperties;

    @Override
    public Object inserFill(String property, Object bean, Class propertyType) {
        Set<String> set = autoFillFieldProperties.getCreateTime();
        if( !set.isEmpty() )
        {
            if( set.contains(property) || set.contains(property.toLowerCase()) || set.contains(property.toUpperCase()) )
            {
                Object val = null;
                try {
                    val = PropertyUtils.getProperty(bean,property);
                } catch (Exception e) {

                }
                if( val == null )
                    val = getValue(propertyType);
                return val;
            }
        }
        return null;
    }

    @Override
    public Object updateFill(String property, Object bean, Class propertyType) {
        return null;
    }

    private Object getValue(Class propertyType)
    {
       if( Date.class.isAssignableFrom(propertyType) )
       {
           return new Date();
       }
       else if( String.class.isAssignableFrom(propertyType))
       {
           return DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss");
       }
       return null;

    }
}
