package io.github.hlg212.fcf.service;

/**
 *
 * @author: Administrator
 * @date: 2022/7/24 8:27
 */
public interface PermissionService {

    public Boolean checkPermission(String appCode,String userId,String permissionCode);

}
