package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.mc.OnlineUser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class OnlineUserApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.OnlineUserApi,name=Constants.ApiName.OnlineUserApi,path =Constants.ApiPath.OnlineUserApi,url =Constants.AppFeignUrl.OnlineUserApi)
    public interface OnlineUserApi extends   io.github.hlg212.fcf.api.OnlineUserApi<OnlineUser> {

    }
}
