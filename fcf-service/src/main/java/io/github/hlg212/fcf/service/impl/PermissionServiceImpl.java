package io.github.hlg212.fcf.service.impl;

import io.github.hlg212.fcf.api.AuthApi;
import io.github.hlg212.fcf.api.LongRunningResApi;
import io.github.hlg212.fcf.cache.Constants;
import io.github.hlg212.fcf.model.ga.ILongRunningRes;
import io.github.hlg212.fcf.service.LongRunningResService;
import io.github.hlg212.fcf.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author hlg
 */
@Component("FCF.PermissionServiceImpl")
@CacheConfig(cacheNames = Constants.Permission,cacheManager = Constants.CacheManager.SimpleCacheManager)
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private AuthApi authApi;

    @Override
    @Cacheable(key = Constants.PermissionResKey.checkPermission_spel,unless="#result == null")
    public Boolean checkPermission(String appCode, String userId, String permissionCode) {
        List<String> codes = null;
        try {
            codes = authApi.getAppPermissions(appCode, userId);
        }catch (Exception e)
        {
            log.warn("读取用户权限失败!",e);
        }
        Boolean flag = false;
        if(!Objects.isNull(codes) )
        {
            flag = codes.contains(permissionCode);
        }
        return flag;
    }

}
