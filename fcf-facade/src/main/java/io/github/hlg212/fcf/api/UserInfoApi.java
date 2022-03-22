package  io.github.hlg212.fcf.api;

import  io.github.hlg212.fcf.model.basic.IUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(contextId = Constants.ApiContextId.UserInfoApi,name=Constants.APP_APIGATEWAY_CAS,path =Constants.APP_CAS_PATH,url =Constants.AppFeignUrl.APP_CAS)
@ConditionalOnExpression("false")
public interface UserInfoApi<T extends  IUser> {

    /**
     * 根据授权编码获得用户信息
     * @param bearerAuthorization
     *
     */
    @RequestMapping(value="/userinfo",method=RequestMethod.GET)
    public T  userinfo(@RequestHeader("Authorization") String bearerAuthorization);

    /**
     * 获取当前用户信息
     * 一般在前端接口调用时使用
     * 目前只支持在web环境调用
     *
     */
    @RequestMapping(value="/userinfo",method=RequestMethod.GET)
    public T  userinfo();
}
