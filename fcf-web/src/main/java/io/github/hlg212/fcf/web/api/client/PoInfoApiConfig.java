package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class PoInfoApiConfig {
    
    @FeignClient(contextId = Constants.ApiContextId.PoInfoApi,name=Constants.ApiName.PoInfoApi, url=Constants.ApiUrl.PoInfoApi)
    public interface PoInfoApi extends  io.github.hlg212.fcf.api.common.PoInfoApi {

    }
}
