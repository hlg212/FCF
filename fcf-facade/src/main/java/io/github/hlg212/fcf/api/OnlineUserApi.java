package  io.github.hlg212.fcf.api;

import  io.github.hlg212.fcf.model.PageInfo;
import  io.github.hlg212.fcf.model.mc.IOnlineUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(contextId = Constants.ApiContextId.OnlineUserApi,name=Constants.ApiName.OnlineUserApi,path =Constants.ApiPath.OnlineUserApi,url =Constants.AppFeignUrl.OnlineUserApi)
@RequestMapping(Constants.ApiMapping.OnlineUserApi)
@ConditionalOnExpression("false")
public interface OnlineUserApi<T extends IOnlineUser> {

    @RequestMapping(value="/getAllOnlineUsers",method=RequestMethod.GET)
    public PageInfo<T> findPage(@RequestParam(name = "username",required = false)String username, @RequestParam("pageNum")int pageNum, @RequestParam("pageSize")int pageSize);

    @RequestMapping(value="/kill", method = {RequestMethod.POST})
    public void kill(@RequestParam("id")String id);

    @RequestMapping(value="/getById",method=RequestMethod.GET)
    public T getById(@RequestParam("id")String id);

    @RequestMapping(value="/isInvalid",method=RequestMethod.GET)
    public boolean isInvalid(@RequestParam("id")String id);

}
