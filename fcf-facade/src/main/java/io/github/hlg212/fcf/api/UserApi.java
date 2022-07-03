package  io.github.hlg212.fcf.api;

import  io.github.hlg212.fcf.model.basic.IUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(contextId = Constants.ApiContextId.UserApi,name=Constants.ApiName.UserApi,path =Constants.ApiPath.UserApi,url =Constants.ApiUrl.UserApi)
@RequestMapping(Constants.ApiMapping.UserApi)
@ConditionalOnExpression("false")
public interface UserApi<T extends IUser> {


    /**
     * 根据账号获得用户
     * @param account
     *
     */
    @RequestMapping(value="/getUserByAccount",method=RequestMethod.GET)
    public T getUserByAccount(@RequestParam("account") String account);


    @RequestMapping(value="/getById",method=RequestMethod.GET)
    public T getById(@RequestParam("id") String id);


    /**
          * 修改当前登录用户密码
     * 
     * @param userId
     * @param oldPassword
     * @param newPassword
     */
    @RequestMapping(value="/changePassword",method=RequestMethod.POST)
    public void changePassword(@RequestParam("userId") String userId,@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword);
}
