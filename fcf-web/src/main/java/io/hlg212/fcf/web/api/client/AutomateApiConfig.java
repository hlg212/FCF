package io.hlg212.fcf.web.api.client;

import io.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;


@Configuration
class AutomateApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.AutomateApi ,name=Constants.APP_APIGATEWAY_BASIC,url =Constants.AppFeignUrl.APP_APPLICATION_URL)
    @RequestMapping(Constants.ApiMapping.AutomateApi)
    public interface AutomateApi extends io.hlg212.fcf.api.AutomateApi{

    }

}
