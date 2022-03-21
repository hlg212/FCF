package io.hlg212.fcf.web.api.client;

import io.hlg212.fcf.api.Constants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class AnonymousResApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.AnonymousResApi,name=Constants.APP_APIGATEWAY_ADMIN,path =Constants.APP_ADMIN_PATH,url =Constants.AppFeignUrl.APP_ADMIN)
    @CacheConfig(cacheNames = io.hlg212.fcf.cache.Constants.AnonymousRes)
    public interface AnonymousResApi extends  io.hlg212.fcf.api.AnonymousResApi {


    }
}
