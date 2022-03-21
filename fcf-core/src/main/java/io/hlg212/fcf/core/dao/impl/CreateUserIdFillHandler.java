package io.hlg212.fcf.core.dao.impl;

import io.hlg212.fcf.core.properties.AutoFillFieldProperties;
import io.hlg212.fcf.dao.FillHandler;
import io.hlg212.fcf.model.basic.IUser;
import io.hlg212.fcf.util.FworkHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class CreateUserIdFillHandler implements FillHandler {

    @Autowired
    private AutoFillFieldProperties autoFillFieldProperties;

    @Override
    public Object inserFill(String property, Object bean, Class propertyType) {

        Set<String> set = autoFillFieldProperties.getCreateUserId();
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
        String userId = "";
        if( user != null && StringUtils.isNotEmpty(user.getId()))
        {
            userId = user.getId();
        }
        return userId;
    }
}
