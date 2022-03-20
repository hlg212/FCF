package com.hlg.fcf.env;

import org.springframework.core.env.PropertySource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DefaultPackagePropertySource extends PropertySource  {

    private static final String PREFIX = "hlg.default.package";
    private static List<String> basePackages = new ArrayList();

    public static void add(String basePackage)
    {
        basePackages.add(basePackage);
    }

    public static List<String> getBasePackages()
    {
        return Collections.unmodifiableList(basePackages);
    }

    public DefaultPackagePropertySource(String name) {
        super(name);
    }

    @Override
    public Object getProperty(String s) {
        if (!s.startsWith(PREFIX)) {
            return null;
        }

        s = s.substring(PREFIX.length()+1);
        if( "dao".equals(s) )
        {
            return getDaos();
        } else if( s.startsWith("basePackage") )
        {
            if( s.startsWith("basePackage[") )
            {
                int b = s.indexOf("[");
                int e = s.indexOf("]");
                int index = Integer.parseInt( s.substring(b+1,e) );
                if( basePackages.size() > index ) {
                    return basePackages.get(index);
                }
                return null;
            }

            return basePackages.toArray( new String[0]);
        }
        return null;
    }

    private String[] getDaos(){
        String[] daos = new String[basePackages.size()];
        for( int i=0;i<basePackages.size();i++ )
        {
            daos[i] = basePackages.get(i) + ".dao";
        }
        return daos;
    }
}
