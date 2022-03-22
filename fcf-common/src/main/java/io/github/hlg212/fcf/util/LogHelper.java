package  io.github.hlg212.fcf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * log工具
 * 动态获取logger
 * 因为在interface无法编写静态代码块，通过该工具可以动态创建
 *
 * @author huangligui
 * @date 2020年8月18日
 */
public class LogHelper {

    private static Map<Class,Logger> cache = new HashMap<>();

    public static Logger getLog(Class cla)
    {
        Logger log = cache.putIfAbsent(cla,LoggerFactory.getLogger(cla));
        if( log == null )
            log = cache.get(cla);
       return log;
    }

}
