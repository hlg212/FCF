package  io.github.hlg212.fcf.util;

import  io.github.hlg212.fcf.model.Constants;


/**
 * 布尔字典映射
 *
 * @author huangligui
 * @date  2022年3月21日 下午2:41:09
 */
public class BooleanHelper {

    public static Boolean to(String str)
    {
        if( Constants.BOOLEAN_Y.equals(str) )
        {
            return true;
        }
        return false;
    }

    public static String to(Boolean bl )
    {
        if( bl != null && bl )
        {
            return Constants.BOOLEAN_Y;
        }
        return Constants.BOOLEAN_N;
    }


}
