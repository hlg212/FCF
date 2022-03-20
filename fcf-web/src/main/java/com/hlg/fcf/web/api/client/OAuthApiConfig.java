package com.hlg.fcf.web.api.client;

import com.hlg.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;


@Configuration
class OAuthApiConfig {


    @FeignClient(contextId = Constants.ApiContextId.OAuthApi,name=Constants.APP_APIGATEWAY_CAS,path =Constants.APP_CAS_PATH,url =Constants.AppFeignUrl.APP_CAS,configuration = OAuthApiConfiguration.class)
    @RequestMapping(Constants.ApiMapping.OAuthApi)
    public interface OAuthApi extends  com.hlg.fcf.api.OAuthApi{

    }


}

