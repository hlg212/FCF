package  io.github.hlg212.fcf.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * 泛型帮助工具
 *
 * @author huangligui
 * @date 2020年5月30日
 */
public class ParameterizedTypeHelper {

    public static Class getParameterizedType(Class ifaceClass,Class childClass)
    {
        List<Class> cs = getParameterizedType(ifaceClass);
        for( Class c : cs )
        {
            if( childClass.isAssignableFrom(c) )
            {
                return c;
            }
        }
        return null;
    }

    private static List<Class> getIfaceParameterizedType( Type types[])
    {
        List<Class> cs = new ArrayList();
        for( Type t : types ) {

            if( t instanceof  ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) types[0];
                Type ptypes[] = parameterizedType.getActualTypeArguments();

                for (int i = 0; i < ptypes.length; i++) {
                    cs.add( (Class) ptypes[i] );
                }
            }
            else if( t instanceof  Class )
            {
                cs.addAll( getIfaceParameterizedType( ((Class) t).getGenericInterfaces() ) );
            }
        }
        return cs;
    }

    private static List<Class> getSuperParameterizedType(Class ifaceClass)
    {
        List<Class> cs = new ArrayList();
        Type t = ifaceClass.getGenericSuperclass();
        if( t instanceof  ParameterizedType)
        {
            ParameterizedType parameterizedType = (ParameterizedType) t;
            Type ptypes[] = parameterizedType.getActualTypeArguments();

            for (int i = 0; i < ptypes.length; i++) {
                cs.add( (Class) ptypes[i] );
            }
        }

        return cs;
    }

    public static List<Class> getParameterizedType(Class ifaceClass)
    {
        List<Class> cs = getIfaceParameterizedType(ifaceClass.getGenericInterfaces());

        cs.addAll( getSuperParameterizedType(ifaceClass) );

        return cs;
    }

    public static Class getParameterizedTypeIndex0(Class ifaceClass)
    {
        List<Class> list = getParameterizedType(ifaceClass);
        if( list.isEmpty() )
        {
            ExceptionHelper.throwServerException(String.format("class: %s 找不到定义的泛型类型！",ifaceClass.getName()));
        }
        Class bc =  list.get(0);
        return bc;
    }
}
