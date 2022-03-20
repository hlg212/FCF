package com.hlg.fcf.util;


import com.hlg.fcf.Constants;
import com.hlg.fcf.env.DefaultPackagePropertySource;
import com.hlg.fcf.exception.BaseException;

import java.util.ArrayList;
import java.util.List;

public class StackTraceHelper {

    private  final static int SIGNIFICANT_ROW = 10;

    private static final String[] SKIPS = new String[]{"$$EnhancerByCGLIB$$","$$EnhancerBySpringCGLIB$$"};

    public static String getSignificantStackTrace(Throwable e)
    {
        List<String> bpackages = new ArrayList<>();
        bpackages.add(Constants.FRAME_BASE_PACKAGE);
        bpackages.addAll(DefaultPackagePropertySource.getBasePackages());
        StringBuilder sb = new StringBuilder();
        sb.append(e).append("\r\n");

        if( e instanceof BaseException && e.getCause() != null)
        {
            e = e.getCause();
            sb.append(e).append("\r\n");
        }
        StackTraceElement[] elements = e.getStackTrace();
        for(String basePackage : bpackages )
        {
            for( int i=0,j=0;i<elements.length && j<=SIGNIFICANT_ROW;i++ )
            {
                String className = elements[i].getClassName();
                boolean flag = isSkip(className);
                if( !flag  && className.startsWith(basePackage) ) {
                    sb.append("\t").append(elements[i].toString()).append("\r\n");
                    j++;
                }
            }
        }

        return sb.toString();
    }

    private static boolean isSkip(String className)
    {
        for( String str : SKIPS )
        {
            if( className.indexOf(str) != -1 )
            {
                return true;
            }
        }
        return false;
    }
}
