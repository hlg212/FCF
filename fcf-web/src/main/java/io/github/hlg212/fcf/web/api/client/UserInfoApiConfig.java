package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.annotation.CacheRead;
import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.basic.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Configuration
class UserInfoApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.UserInfoApi,name=Constants.ApiName.UserInfoApi,path =Constants.ApiPath.UserInfoApi,url =Constants.ApiUrl.UserInfoApi)
    @CacheConfig(cacheNames =  io.github.hlg212.fcf.cache.Constants.UserInfo)
    public interface UserInfoApi extends   io.github.hlg212.fcf.api.UserInfoApi<User> {

        @RequestMapping(value="/userinfo",method=RequestMethod.GET)
        @CacheRead(key = "#p0.substring(7)")
        @Override
        public User userinfo(@RequestHeader("Authorization") String bearerAuthorization);
    }
}
