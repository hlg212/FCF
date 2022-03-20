package com.hlg.fcf.api;

import com.hlg.fcf.CurdClientApi;
import com.hlg.fcf.model.Qco;
import com.hlg.fcf.model.basic.IUserLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = Constants.ApiContextId.UserLockApi,name=Constants.APP_APIGATEWAY_CAS,path =Constants.APP_CAS_PATH,url =Constants.AppFeignUrl.APP_CAS)
@RequestMapping(Constants.ApiMapping.UserLockApi)
@ConditionalOnExpression("false")
public interface UserLockApi<T extends IUserLock,Q extends Qco> extends CurdClientApi<T,Q>  {

    /**
     * 是否锁定
     * @param username
     * @return
     */
    @RequestMapping(value="/isLock",method=RequestMethod.GET)
    public boolean isLock(@RequestParam("username") String username);


}
