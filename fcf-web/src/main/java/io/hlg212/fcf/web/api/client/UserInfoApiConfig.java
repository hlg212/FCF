package io.hlg212.fcf.web.api.client;

import io.hlg212.fcf.annotation.CacheableReadOnly;
import io.hlg212.fcf.api.Constants;
import io.hlg212.fcf.model.basic.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Configuration
class UserInfoApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.UserInfoApi,name=Constants.APP_APIGATEWAY_CAS,path =Constants.APP_CAS_PATH,url =Constants.AppFeignUrl.APP_CAS)
    @CacheConfig(cacheNames = io.hlg212.fcf.cache.Constants.UserInfo)
    public interface UserInfoApi extends  io.hlg212.fcf.api.UserInfoApi<User> {

        @RequestMapping(value="/userinfo",method=RequestMethod.GET)
        @CacheableReadOnly(key = "#p0.substring(7)")
        @Override
        public User userinfo(@RequestHeader("Authorization") String bearerAuthorization);
    }
}
