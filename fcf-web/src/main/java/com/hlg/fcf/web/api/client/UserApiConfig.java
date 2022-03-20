package com.hlg.fcf.web.api.client;

import com.hlg.fcf.annotation.CacheableReadOnly;
import com.hlg.fcf.api.Constants;
import com.hlg.fcf.model.basic.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static com.hlg.fcf.api.Constants.APP_BASIC_PATH;


@Configuration
class UserApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.UserApi,name=Constants.APP_APIGATEWAY_BASIC,path =APP_BASIC_PATH,url =Constants.AppFeignUrl.APP_BASIC )
    @CacheConfig(cacheNames = com.hlg.fcf.cache.Constants.User)
    public interface UserApi extends com.hlg.fcf.api.UserApi<User>{

        /**
         * 根据账号获得用户
         * @param account
         * @return
         */
        @RequestMapping(value="/getUserByAccount",method=RequestMethod.GET)
        @CacheableReadOnly(key = com.hlg.fcf.cache.Constants.UserKey.getUserByAccount_spel)
        @Override
        public User getUserByAccount(@RequestParam("account") String account);

        @RequestMapping(value="/getById",method=RequestMethod.GET)
        @Override
        @CacheableReadOnly(key = "#p0")
        public User getById(@RequestParam("id") String id);
    }
}
