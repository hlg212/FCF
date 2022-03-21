package io.hlg212.fcf.util;

import io.hlg212.fcf.Constants;
import io.hlg212.fcf.env.DefaultPackagePropertySource;

import java.util.ArrayList;
import java.util.List;


/**
 * 框架扫描包工具
 *
 * @author huangligui
 * @date 2020年8月18日
 */
public class ScanPackageHelper {

    public static List<String> getBasePackages()
    {
        return DefaultPackagePropertySource.getBasePackages();
    }

    public static List<String> getPackages()
    {
        List<String> packages = new ArrayList();
        packages.add(Constants.FRAME_BASE_PACKAGE);
        packages.addAll(getBasePackages());
        return packages;
    }
}
