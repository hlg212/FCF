package com.hlg.fcf.util;

import com.hlg.fcf.model.basic.IDic;
import com.hlg.fcf.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
/**
 * 字典工具类
 *
 * @author wuwei
 * @date 2018年11月20日
 */
public class DicUtils {
	
    private static DicUtils d_instance;
    
    @Autowired
    private DicService dicService;

    public static String getDicVal(String key){
        return getDicVal(AppContextHelper.getAppCode(), key);
    }

    public static String getDicVal(String appcode, String key){
        return getDicService().getVal(appcode, key);
    }

    public static String getDicName(String key){
        return getDicName(AppContextHelper.getAppCode(), key);
    }

    public static String getDicName(String appcode, String key){
        return getDicService().getName(appcode, key);
    }

    public static Map<String, String> getDicMapVal(String key){ return getDicMapVal(AppContextHelper.getAppCode(), key); }

    public static Map<String, String> getDicMapVal(String appcode, String key){ return getDicService().getMapVal(appcode, key); }

    public static Map<String, String> getDicMapName(String key){ return getDicMapName(AppContextHelper.getAppCode(),key); }

    public static Map<String, String> getDicMapName(String appcode, String key){ return getDicService().getMapName(appcode, key); }

    public static List<IDic> getAllDic() { return getAllDic(AppContextHelper.getAppCode()); }

    public static List<IDic> getAllDic(String code) { return getDicService().getAllDic(code); }

    public static String getDicChildValByName(String parantKey,String name){ return getDicChildValByName(AppContextHelper.getAppCode(), parantKey,name); }

    public static String getDicChildValByName(String appcode, String parantKey,String name){
        try {
                Map<String,String> names = getDicService().getMapName(appcode, parantKey);
                for( Map.Entry<String,String>  entry : names.entrySet() )
                {
                    if( entry.getValue().equals(name) )
                    {
                        return getDicVal(appcode,entry.getKey());
                    }
                }
        }catch (Exception e){
        }
        return null;
    }

    public static String getDicChildNameByVal(String parantKey,String val){ return getDicChildNameByVal(AppContextHelper.getAppCode(),parantKey,val); }

    public static String getDicChildNameByVal(String appcode, String parantKey,String val){
        try {
                Map<String,String> names = getDicService().getMapVal(appcode, parantKey);
                for( Map.Entry<String,String>  entry : names.entrySet() )
                {
                    if( entry.getValue().equals(val) )
                    {
                        return getDicName(appcode,entry.getKey());
                    }
                }
        }catch (Exception e){
        }
        return null;
    }



    private static DicService getDicService()
    {
        if( d_instance == null )
        {
            d_instance = SpringUtils.getBean(DicUtils.class);
        }
        return d_instance.dicService;
    }


}
