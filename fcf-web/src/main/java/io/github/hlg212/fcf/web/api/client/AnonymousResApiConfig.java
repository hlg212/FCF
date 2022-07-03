package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class AnonymousResApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.AnonymousResApi ,name=Constants.ApiName.AnonymousResApi,path =Constants.ApiPath.AnonymousResApi,url =Constants.ApiUrl.AnonymousResApi)
    @CacheConfig(cacheNames =  io.github.hlg212.fcf.cache.Constants.AnonymousRes)
    public interface AnonymousResApi extends   io.github.hlg212.fcf.api.AnonymousResApi {


    }
}
