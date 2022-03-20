package com.hlg.fcf.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.SystemPropertyUtils;

@Component
public class EnvironmentHelper {

    private static EnvironmentHelper _instance;

    @Autowired
    private Environment environment;

    private static EnvironmentHelper getInstance()
    {
        if( _instance == null )
        {
            _instance = SpringUtils.getBean(EnvironmentHelper.class);
        }
        return _instance;
    }

    public static String resolvePlaceholders(String value)
    {
       return getInstance()._resolvePlaceholders(value);
    }

    private String _resolvePlaceholders(String value) {

        if(value.startsWith(SystemPropertyUtils.PLACEHOLDER_PREFIX)) {
            String result = environment.resolvePlaceholders(value);
            return result != null ? result : "";
        }
        return value;
    }
}
