package io.hlg212.fcf.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.SystemPropertyUtils;

/**
 * spring 配置参数帮助工具
 *
 * @author: huangligui
 * @create: 2019-02-25 16:01
 **/
@Component
public class EnvironmentHelper {

    private static EnvironmentHelper _instance;

    @Autowired
    private Environment environment;

    private static EnvironmentHelper getInstance()
    {
        if( _instance == null )
        {
            _instance = SpringHelper.getBean(EnvironmentHelper.class);
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
