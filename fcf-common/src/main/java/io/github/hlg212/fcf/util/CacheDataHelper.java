package  io.github.hlg212.fcf.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个缓存帮助工具
 * 不要在其中存入业务数据，该缓存不会清空，只作为框架缓存使用
 *
 * @author huangligui
 * @date 2020年8月18日
 */
public class CacheDataHelper {

    private static Map cache = new HashMap<>();

    public static <O extends  Object> O get(Object key)
    {
        return (O) cache.get(key);
    }

    public static void put(Object key,Object val)
    {
        cache.put(key,val);
    }

}
