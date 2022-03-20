package com.hlg.fcf.util;

import com.hlg.fcf.model.Constants;

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
