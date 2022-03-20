package com.hlg.fcf.web.api.client;

import com.hlg.fcf.api.Constants;
import com.hlg.fcf.model.mc.OnlineUser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class OnlineUserApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.OnlineUserApi,name=Constants.APP_APIGATEWAY_CAS,path =Constants.APP_CAS_PATH,url =Constants.AppFeignUrl.APP_CAS)
    public interface OnlineUserApi extends  com.hlg.fcf.api.OnlineUserApi<OnlineUser> {

    }
}
