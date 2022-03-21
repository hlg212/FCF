package io.hlg212.fcf.web.api.client;

import io.hlg212.fcf.api.Constants;
import io.hlg212.fcf.model.Qco;
import io.hlg212.fcf.model.basic.UserLock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;


@Configuration
class UserLockApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.UserLockApi,name=Constants.APP_APIGATEWAY_CAS,path =Constants.APP_CAS_PATH,url =Constants.AppFeignUrl.APP_CAS)
    @RequestMapping(Constants.ApiMapping.UserLockApi)
    public interface UserLockApi extends  io.hlg212.fcf.api.UserLockApi<UserLock,Qco> {

    }
}
