package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;


@Configuration
class OAuthApiConfig {


    @FeignClient(contextId = Constants.ApiContextId.OAuthApi, name = Constants.ApiName.OAuthApi, path = Constants.ApiPath.OAuthApi, url = Constants.AppFeignUrl.OAuthApi,configuration = OAuthApiConfiguration.class)
    @RequestMapping(Constants.ApiMapping.OAuthApi)
    public interface OAuthApi extends   io.github.hlg212.fcf.api.OAuthApi{

    }


}

