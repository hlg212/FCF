package  io.github.hlg212.fcf.core.dao.impl;

import  io.github.hlg212.fcf.core.properties.AutoFillFieldProperties;
import io.github.hlg212.fcf.core.handler.FillHandler;
import  io.github.hlg212.fcf.model.basic.IUser;
import  io.github.hlg212.fcf.util.FworkHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class UpdateUserIdFillHandler implements FillHandler {

    @Autowired
    private AutoFillFieldProperties autoFillFieldProperties;

    @Override
    public Object inserFill(String property, Object bean, Class propertyType) {

        return updateFill(property,bean,propertyType);
    }

    @Override
    public Object updateFill(String property, Object bean, Class propertyType) {
        Set<String> set = autoFillFieldProperties.getUpdateUserId();
        if( !set.isEmpty() )
        {
            if( set.contains(property) || set.contains(property.toLowerCase()) || set.contains(property.toUpperCase()) )
            {
                Object val = getValue();
                if( val == null || "".equals(val)) {
                    try {
                        val = PropertyUtils.getProperty(bean, property);
                    } catch (Exception e) {

                    }
                }
                return  val;
            }
        }
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
