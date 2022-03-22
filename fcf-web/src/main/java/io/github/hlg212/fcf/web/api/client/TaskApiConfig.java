package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class TaskApiConfig {

    /**
     * @author wuwei
     * @date 2019年2月22日
     */
    @FeignClient(contextId = Constants.ApiContextId.TaskApi,name=Constants.APP_APIGATEWAY_TASK,url=Constants.AppFeignUrl.APP_TASK)
    public interface TaskApi extends  io.github.hlg212.fcf.api.common.TaskApi {

    }
}
