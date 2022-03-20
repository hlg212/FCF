package com.hlg.fcf.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * json帮助工具
 *
 * @author huangligui
 * @date 2020年5月30日
 */
public class JsonHelper {

    private static ObjectMapper objectMapper = null;

    private static ObjectMapper getObjectMapper()
    {
        if(objectMapper == null)
        {
            //objectMapper = SpringUtils.getBean(ObjectMapper.class);
            //if( objectMapper == null )
           // {
                objectMapper = new ObjectMapper();
                objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
           // }
        }
        return objectMapper;
    }

    public static Object parseObject(String json)
    {
           return parseObject(json,Object.class);
    }

    public static <T> T parseObject(String json, Class<T> aclass)
    {
        try {
            return getObjectMapper().readValue(json,aclass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object toJsonObject(Object obj)
    {
        try {
            return parseObject( getObjectMapper().writeValueAsString(obj) );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  <T> T toJsonObject(Object obj,Class<T> cl)
    {
        try {
            return parseObject( getObjectMapper().writeValueAsString(obj),cl );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  String toJson(Object obj)
    {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
