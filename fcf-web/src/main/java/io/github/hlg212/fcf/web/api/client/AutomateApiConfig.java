package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;


@Configuration
class AutomateApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.AutomateApi ,name=Constants.ApiName.AutomateApi,url =Constants.AppFeignUrl.AutomateApi)
    @RequestMapping(Constants.ApiMapping.AutomateApi)
    public interface AutomateApi extends  io.github.hlg212.fcf.api.AutomateApi{

    }

}
