package  io.github.hlg212.fcf.core.dao.impl;

import  io.github.hlg212.fcf.core.properties.AutoFillFieldProperties;
import  io.github.hlg212.fcf.dao.FillHandler;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Set;

public class UpdateTimeFillHandler implements FillHandler {

    @Autowired
    private AutoFillFieldProperties autoFillFieldProperties;

    @Override
    public Object inserFill(String property, Object bean, Class propertyType) {
        return updateFill(property,bean,propertyType);
    }

    @Override
    public Object updateFill(String property, Object bean, Class propertyType) {

        Set<String> set = autoFillFieldProperties.getUpdateTime();
        if( !set.isEmpty() )
        {
            if( set.contains(property) || set.contains(property.toLowerCase()) || set.contains(property.toUpperCase()) )
            {
                Object val = getValue(propertyType);
                if( val == null || "".equals(val)) {
                    try {
                        val = PropertyUtils.getProperty(bean, property);
                    } catch (Exception e) {

                    }
                }
                return val;
            }
        }
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
