package com.hlg.fcf.web.api.client;

import com.hlg.fcf.annotation.CacheableReadOnly;
import com.hlg.fcf.api.Constants;
import com.hlg.fcf.model.basic.App;
import com.hlg.fcf.model.basic.Dic;
import com.hlg.fcf.model.basic.IApp;
import com.hlg.fcf.model.basic.Res;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Configuration
class AuthApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.AuthApi,name=Constants.APP_APIGATEWAY_BASIC,path =Constants.APP_BASIC_PATH,url =Constants.AppFeignUrl.APP_BASIC)
    @CacheConfig(cacheNames = com.hlg.fcf.cache.Constants.Auth)
    public interface AuthApi extends com.hlg.fcf.api.AuthApi{

        /**
         * 获得所有需要授权的资源
         * @return
         */
        @Override
        @RequestMapping(value="/getAllAuthRes",method=RequestMethod.GET)
        @CacheableReadOnly(key = com.hlg.fcf.cache.Constants.AuthKey.getAllAuthRes_spel)
        public List<Res> getAllAuthRes();

        /**
         *  获得权限资源列表
         * @param userId
         * @return
         */
        @Override
        @RequestMapping(value="/getMenuByUserId",method=RequestMethod.GET)
        public List<Res>  getMenuByUserId(@RequestParam("appCode") String appCode, @RequestParam("userId") String userId);

        /**
         *  获得用户所拥有的app
         * @param userId
         * @return
         */
        @RequestMapping(value="/getAppsByUserId",method=RequestMethod.GET)
        public List<App> getAppsByUserId(@RequestParam("userId") String userId, @RequestParam("type") String type);
    }

}
