package com.hlg.fcf.util;

import org.springframework.web.method.HandlerTypePredicate;

import java.util.List;

/**
 *
 * @author huangligui
 * @date 2020年8月18日
 */
public class HandlerTypePredicateHelper {

    private HandlerTypePredicate beanTypePredicate;

    private static HandlerTypePredicateHelper _instance;

    private static HandlerTypePredicateHelper getInstance()
    {
        if( _instance == null )
        {
            _instance = new HandlerTypePredicateHelper();

        }
        return _instance;
    }

    private HandlerTypePredicateHelper()
    {
        List<String> basePackages = ScanPackageHelper.getPackages();
        beanTypePredicate = HandlerTypePredicate.builder()
                .basePackage(basePackages.toArray(new String[0]))
                .build();
    }

    public static boolean test(Class<?> cla)
    {
        return getInstance().beanTypePredicate.test(cla);
    }
}
