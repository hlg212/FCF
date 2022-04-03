package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class TaskApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.TaskApi,name=Constants.ApiName.TaskApi, url=Constants.AppFeignUrl.TaskApi)
    public interface TaskApi extends  io.github.hlg212.fcf.api.common.TaskApi {

    }
}
