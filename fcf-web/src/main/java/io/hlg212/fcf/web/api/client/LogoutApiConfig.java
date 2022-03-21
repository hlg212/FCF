package io.hlg212.fcf.web.api.client;

import io.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;


@Configuration
class LogoutApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.LogoutApi,name=Constants.APP_APIGATEWAY_CAS,path =Constants.APP_CAS_PATH,url =Constants.AppFeignUrl.APP_CAS)
    @RequestMapping(Constants.ApiMapping.LogoutApi)
    public interface LogoutApi extends  io.hlg212.fcf.api.LogoutApi {

    }

}
