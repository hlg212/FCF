package  io.github.hlg212.fcf.api;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(contextId = Constants.ApiContextId.LogoutApi,name=Constants.ApiName.LogoutApi,path =Constants.ApiPath.LogoutApi,url =Constants.AppFeignUrl.LogoutApi)
@RequestMapping(Constants.ApiMapping.LogoutApi)
@ConditionalOnExpression("false")
public interface LogoutApi {

    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public void logout(@RequestParam(value = "token",required = false)String token);
}
