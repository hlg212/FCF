package io.hlg212.fcf.web.api.client;

import io.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class PartitionApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.PartitionApi,name=Constants.APP_APIGATEWAY_RTP,path =Constants.APP_RTP_PATH,url =Constants.AppFeignUrl.APP_RTP)
    public interface PartitionApi extends io.hlg212.fcf.api.rtp.PartitionApi {


    }

}
