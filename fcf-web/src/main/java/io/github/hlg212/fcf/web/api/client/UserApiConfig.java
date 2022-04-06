package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.annotation.CacheRead;
import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.basic.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Configuration
class UserApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.UserApi,name=Constants.ApiName.UserApi,path =Constants.ApiPath.UserApi,url =Constants.AppFeignUrl.UserApi)
    @CacheConfig(cacheNames =  io.github.hlg212.fcf.cache.Constants.User)
    public interface UserApi extends  io.github.hlg212.fcf.api.UserApi<User>{

        /**
         * 根据账号获得用户
         * @param account
         *
         */
        @RequestMapping(value="/getUserByAccount",method=RequestMethod.GET)
        @CacheRead(key =  io.github.hlg212.fcf.cache.Constants.UserKey.getUserByAccount_spel)
        @Override
        public User getUserByAccount(@RequestParam("account") String account);

        @RequestMapping(value="/getById",method=RequestMethod.GET)
        @Override
        @CacheRead(key = "#p0")
        public User getById(@RequestParam("id") String id);
    }
}
