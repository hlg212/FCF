package  io.github.hlg212.fcf.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

public class FastJsonHelper {
    private final static ParserConfig defaultRedisConfig = new ParserConfig();

    static { defaultRedisConfig.setAutoTypeSupport(true);}


    public static Object parseObject(String json)
    {
        return JSON.parseObject(json, Object.class, defaultRedisConfig);
    }

    public static <T> T parseObject(String json, Class<T> aclass)
    {
        return JSON.parseObject(json, aclass);
    }

    public static String toJSONString(Object obj )
    {
        return JSON.toJSONString(obj);
    }

}
