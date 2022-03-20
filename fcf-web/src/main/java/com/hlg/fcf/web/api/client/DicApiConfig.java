package com.hlg.fcf.web.api.client;

import com.hlg.fcf.annotation.CacheableReadOnly;
import com.hlg.fcf.api.Constants;
import com.hlg.fcf.model.basic.Dic;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Configuration
class DicApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.DicApi,name=Constants.APP_APIGATEWAY_BASIC,path =Constants.APP_BASIC_PATH,url =Constants.AppFeignUrl.APP_BASIC)
    @CacheConfig(cacheNames = com.hlg.fcf.cache.Constants.Dic)
    public interface DicApi extends com.hlg.fcf.api.DicApi<Dic>{

        @RequestMapping(value="/getAllDics",method=RequestMethod.GET)
        @CacheableReadOnly(key = com.hlg.fcf.cache.Constants.DicKey.getAllDics_spel)
        @Override
        public List<Dic> getAllDics(@RequestParam("appCode") String appCode);


        @RequestMapping(value="/getById",method=RequestMethod.GET)
        @CacheableReadOnly(key = "#p0")
        @Override
        public Dic getById(@RequestParam("id") String id);
    }

}
