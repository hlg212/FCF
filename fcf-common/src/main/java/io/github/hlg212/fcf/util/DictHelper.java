package  io.github.hlg212.fcf.util;

import io.github.hlg212.fcf.model.basic.IDict;
import io.github.hlg212.fcf.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * 字典工具类
 *
 * @author huangligui
 * @date 2020年5月30日
 */
@Component
public class DictHelper {
	
    private static DictHelper _instance;
    
    @Autowired
    private DictService DictService;

    public static String getDictVal(String key){
        return getDictVal(AppContextHelper.getAppCode(), key);
    }

    public static String getDictVal(String appcode, String key){
        return getDictService().getVal(appcode, key);
    }

    public static String getDictName(String key){
        return getDictName(AppContextHelper.getAppCode(), key);
    }

    public static String getDictName(String appcode, String key){
        return getDictService().getName(appcode, key);
    }

    public static Map<String, String> getDictMapVal(String key){ return getDictMapVal(AppContextHelper.getAppCode(), key); }

    public static Map<String, String> getDictMapVal(String appcode, String key){ return getDictService().getMapVal(appcode, key); }

    public static Map<String, String> getDictMapName(String key){ return getDictMapName(AppContextHelper.getAppCode(),key); }

    public static Map<String, String> getDictMapName(String appcode, String key){ return getDictService().getMapName(appcode, key); }

    public static List<IDict> getAllDicts() { return getAllDicts(AppContextHelper.getAppCode()); }

    public static List<IDict> getAllDicts(String code) { return getDictService().getAllDicts(code); }

    public static String getDictChildValByName(String parantKey,String name){ return getDictChildValByName(AppContextHelper.getAppCode(), parantKey,name); }

    public static String getDictChildValByName(String appcode, String parantKey,String name){
        try {
                Map<String,String> names = getDictService().getMapName(appcode, parantKey);
                for( Map.Entry<String,String>  entry : names.entrySet() )
                {
                    if( entry.getValue().equals(name) )
                    {
                        return getDictVal(appcode,entry.getKey());
                    }
                }
        }catch (Exception e){
        }
        return null;
    }

    public static String getDictChildNameByVal(String parantKey,String val){ return getDictChildNameByVal(AppContextHelper.getAppCode(),parantKey,val); }

    public static String getDictChildNameByVal(String appcode, String parantKey,String val){
        try {
                Map<String,String> names = getDictService().getMapVal(appcode, parantKey);
                for( Map.Entry<String,String>  entry : names.entrySet() )
                {
                    if( entry.getValue().equals(val) )
                    {
                        return getDictName(appcode,entry.getKey());
                    }
                }
        }catch (Exception e){
        }
        return null;
    }



    private static DictService getDictService()
    {
        if( _instance == null )
        {
            _instance = SpringHelper.getBean(DictHelper.class);
        }
        return _instance.DictService;
    }


}
