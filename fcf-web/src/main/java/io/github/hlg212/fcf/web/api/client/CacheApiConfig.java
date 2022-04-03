package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class CacheApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.CacheApi,name=Constants.ApiName.CacheApi, url=Constants.AppFeignUrl.CacheApi)
    public interface CacheApi extends  io.github.hlg212.fcf.api.common.CacheApi {

    }

}
