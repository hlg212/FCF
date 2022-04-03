package  io.github.hlg212.fcf.api;

import  io.github.hlg212.fcf.CurdClientApi;
import  io.github.hlg212.fcf.model.Qco;
import  io.github.hlg212.fcf.model.basic.IUserLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = Constants.ApiContextId.UserLockApi,name=Constants.ApiName.UserLockApi,path =Constants.ApiPath.UserLockApi,url =Constants.AppFeignUrl.UserLockApi)
@RequestMapping(Constants.ApiMapping.UserLockApi)
@ConditionalOnExpression("false")
public interface UserLockApi<T extends IUserLock,Q extends Qco> extends CurdClientApi<T,Q>  {

    /**
     * 是否锁定
     * @param username
     *
     */
    @RequestMapping(value="/isLock",method=RequestMethod.GET)
    public boolean isLock(@RequestParam("username") String username);


}
