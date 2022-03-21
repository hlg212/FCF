package io.hlg212.fcf.web.api.client;

import io.hlg212.fcf.annotation.CacheableReadOnly;
import io.hlg212.fcf.api.Constants;
import io.hlg212.fcf.model.basic.Dic;
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
    @CacheConfig(cacheNames = io.hlg212.fcf.cache.Constants.Dic)
    public interface DicApi extends io.hlg212.fcf.api.DicApi<Dic>{

        @RequestMapping(value="/getAllDics",method=RequestMethod.GET)
        @CacheableReadOnly(key = io.hlg212.fcf.cache.Constants.DicKey.getAllDics_spel)
        @Override
        public List<Dic> getAllDics(@RequestParam("appCode") String appCode);


        @RequestMapping(value="/getById",method=RequestMethod.GET)
        @CacheableReadOnly(key = "#p0")
        @Override
        public Dic getById(@RequestParam("id") String id);
    }

}
