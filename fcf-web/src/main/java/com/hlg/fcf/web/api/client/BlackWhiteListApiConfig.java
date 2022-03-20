package com.hlg.fcf.web.api.client;

import com.hlg.fcf.annotation.CacheableReadOnly;
import com.hlg.fcf.api.Constants;
import com.hlg.fcf.model.basic.BlackWhiteList;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Configuration
class BlackWhiteListApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.BlackWhiteListApi,name=Constants.APP_APIGATEWAY_ADMIN,path =Constants.APP_ADMIN_PATH,url =Constants.AppFeignUrl.APP_ADMIN)
    @CacheConfig(cacheNames = com.hlg.fcf.cache.Constants.BlackWhiteList)
    public interface BlackWhiteListApi extends com.hlg.fcf.api.BlackWhiteListApi<BlackWhiteList>{

        @RequestMapping(value="/getBlackWhiteList",method= RequestMethod.GET)
        @CacheableReadOnly(key = com.hlg.fcf.cache.Constants.BlackWhiteListKey.getBlackWhiteList_spel)
        @Override
        public List<BlackWhiteList> getBlackWhiteList();

        @RequestMapping(value="/getBlackWhiteListByAppCode",method=RequestMethod.GET)
        @CacheableReadOnly(key = com.hlg.fcf.cache.Constants.BlackWhiteListKey.getBlackWhiteListByAppCode_spel)
        @Override
        public List<BlackWhiteList> getBlackWhiteListByAppCode(@RequestParam("appCode") String appCode);

        @RequestMapping(value="/getBlackWhiteListByAccount",method=RequestMethod.GET)
        @CacheableReadOnly(key = com.hlg.fcf.cache.Constants.BlackWhiteListKey.getBlackWhiteListByAccount_spel)
        @Override
        public List<BlackWhiteList> getBlackWhiteListByAccount(@RequestParam("account") String account);
    }
}
