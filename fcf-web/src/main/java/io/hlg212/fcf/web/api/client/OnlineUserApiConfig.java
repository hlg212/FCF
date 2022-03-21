package io.hlg212.fcf.web.api.client;

import io.hlg212.fcf.api.Constants;
import io.hlg212.fcf.model.mc.OnlineUser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class OnlineUserApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.OnlineUserApi,name=Constants.APP_APIGATEWAY_CAS,path =Constants.APP_CAS_PATH,url =Constants.AppFeignUrl.APP_CAS)
    public interface OnlineUserApi extends  io.hlg212.fcf.api.OnlineUserApi<OnlineUser> {

    }
}
