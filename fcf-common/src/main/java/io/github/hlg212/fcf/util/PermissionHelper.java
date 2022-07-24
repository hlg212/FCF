package io.github.hlg212.fcf.util;

import io.github.hlg212.fcf.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

/**
 * 权限工具
 *
 * @author: Administrator
 * @date: 2022/7/24 8:43
 */
@Slf4j
public class PermissionHelper {


    private static PermissionService permissionService;

    private PermissionHelper()
    {

    }

    private static PermissionService getPermissionService()
    {
        if(Objects.isNull(permissionService)) {
            permissionService = SpringHelper.getBean(PermissionService.class);
        }
        return permissionService;
    }

    /**
     * 验证当前用户的当前服务是否拥有某权限
     *
     * @param permissionCode 权限代码
     * @return 拥有返回true;
     */
    public static Boolean checkPermission(String permissionCode) {
        return checkPermission(FworkHelper.getUid(),permissionCode);
    }

    /**
     * 验证用户的当前服务是否拥有某权限
     *
     * @param userId 用户id
     * @param permissionCode 权限代码
     * @return 拥有返回true
     */
    public static Boolean checkPermission(String userId, String permissionCode) {
        String appCode = AppContextHelper.getAppCode();
        return  checkPermission(appCode,userId,permissionCode);
    }

    /**
     * 验证用户的当前服务是否拥有某权限
     *
     * @param userId 用户id
     * @param permissionCode 权限代码
     * @return 拥有返回true
     */
    public static Boolean checkPermission(String appCode,String userId, String permissionCode) {
        if(StringUtils.isEmpty(userId))
        {
            ExceptionHelper.throwBusinessException("找不到登陆用户信息,权限验证失败!");
        }
        return  getPermissionService().checkPermission(appCode,userId,permissionCode);
    }
}
