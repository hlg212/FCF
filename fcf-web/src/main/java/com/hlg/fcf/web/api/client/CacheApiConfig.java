package com.hlg.fcf.web.api.client;

import com.hlg.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class CacheApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.CacheApi,name=Constants.APP_APIGATEWAY_BASIC, url=Constants.AppFeignUrl.APP_APPLICATION_URL)
    public interface CacheApi extends com.hlg.fcf.api.common.CacheApi {

    }

}
