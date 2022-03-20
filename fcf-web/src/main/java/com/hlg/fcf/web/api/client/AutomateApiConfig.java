package com.hlg.fcf.web.api.client;

import com.hlg.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;


@Configuration
class AutomateApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.AutomateApi ,name=Constants.APP_APIGATEWAY_BASIC,url =Constants.AppFeignUrl.APP_APPLICATION_URL)
    @RequestMapping(Constants.ApiMapping.AutomateApi)
    public interface AutomateApi extends com.hlg.fcf.api.AutomateApi{

    }

}
