package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;


@Configuration
class LogoutApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.LogoutApi,name=Constants.ApiName.LogoutApi,path =Constants.ApiPath.LogoutApi,url =Constants.AppFeignUrl.LogoutApi)
    @RequestMapping(Constants.ApiMapping.LogoutApi)
    public interface LogoutApi extends   io.github.hlg212.fcf.api.LogoutApi {

    }

}
