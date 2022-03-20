package com.hlg.fcf.web.api.client;

import com.hlg.fcf.api.Constants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class AnonymousResApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.AnonymousResApi,name=Constants.APP_APIGATEWAY_ADMIN,path =Constants.APP_ADMIN_PATH,url =Constants.AppFeignUrl.APP_ADMIN)
    @CacheConfig(cacheNames = com.hlg.fcf.cache.Constants.AnonymousRes)
    public interface AnonymousResApi extends  com.hlg.fcf.api.AnonymousResApi {


    }
}
