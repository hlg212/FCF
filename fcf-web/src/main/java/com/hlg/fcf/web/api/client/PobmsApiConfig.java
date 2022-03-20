package com.hlg.fcf.web.api.client;

import com.hlg.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class PobmsApiConfig {

    /**
     * @author wuwei
     * @date 2019年9月12日
     */
    @FeignClient(contextId = Constants.ApiContextId.PobmsApi,name=Constants.APP_APIGATEWAY_BASIC, url=Constants.AppFeignUrl.APP_APPLICATION_URL)
    public interface PobmsApi extends com.hlg.fcf.api.common.PobmsApi {

    }
}
