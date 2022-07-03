package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class PartitionApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.PartitionApi,name=Constants.ApiName.PartitionApi,path =Constants.ApiPath.PartitionApi,url =Constants.ApiUrl.PartitionApi)
    public interface PartitionApi extends  io.github.hlg212.fcf.api.rtp.PartitionApi {


    }

}
