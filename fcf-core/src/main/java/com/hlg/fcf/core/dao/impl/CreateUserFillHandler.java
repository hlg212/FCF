package com.hlg.fcf.core.dao.impl;

import com.hlg.fcf.core.properties.AutoFillFieldProperties;
import com.hlg.fcf.dao.FillHandler;
import com.hlg.fcf.model.basic.IUser;
import com.hlg.fcf.util.FworkHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class CreateUserFillHandler implements FillHandler {

    @Autowired
    private AutoFillFieldProperties autoFillFieldProperties;

    @Override
    public Object inserFill(String property, Object bean, Class propertyType) {

        Set<String> set = autoFillFieldProperties.getCreateUser();
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
                    val = getValue();
                return val;
            }
        }

        return null;
    }

    @Override
    public Object updateFill(String property, Object bean, Class propertyType) {
        return null;
    }

    private Object getValue()
    {
        IUser user = FworkHelper.getUser();
        String userName = "";
        if( user != null && StringUtils.isNotEmpty(user.getName()))
        {
            userName = user.getName();
        }
        return userName;
    }
}
