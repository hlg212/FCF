package io.hlg212.fcf.api;

import io.hlg212.fcf.model.basic.IUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(contextId = Constants.ApiContextId.UserApi,name=Constants.APP_APIGATEWAY_BASIC,path =Constants.APP_BASIC_PATH,url =Constants.AppFeignUrl.APP_BASIC)
@RequestMapping(Constants.ApiMapping.UserApi)
@ConditionalOnExpression("false")
public interface UserApi<T extends IUser> {


    /**
     * 根据账号获得用户
     * @param account
     * @return
     */
    @RequestMapping(value="/getUserByAccount",method=RequestMethod.GET)
    public T getUserByAccount(@RequestParam("account") String account);


    @RequestMapping(value="/getById",method=RequestMethod.GET)
    public T getById(@RequestParam("id") String id);


    /**
          * 修改当前登录用户密码
     * 
     * @param yhid
     * @param oldPassword
     * @param newPassword
     * @author ccfhn-xiequn 
     */
    @RequestMapping(value="/changePassword",method=RequestMethod.GET)
    public void changePassword(@RequestParam("yhid") String yhid,@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword);
}
