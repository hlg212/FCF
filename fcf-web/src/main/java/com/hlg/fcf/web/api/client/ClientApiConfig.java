package com.hlg.fcf.web.api.client;

import com.hlg.fcf.annotation.CacheableReadOnly;
import com.hlg.fcf.api.Constants;
import com.hlg.fcf.model.basic.Client;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Configuration
class ClientApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.ClientApi,name=Constants.APP_APIGATEWAY_BASIC,path =Constants.APP_BASIC_PATH,url =Constants.AppFeignUrl.APP_BASIC)
    @CacheConfig(cacheNames = com.hlg.fcf.cache.Constants.Client)
    public interface ClientApi extends com.hlg.fcf.api.ClientApi<Client>{

        @RequestMapping(value="/getById",method= RequestMethod.GET)
        @CacheableReadOnly(key = "#p0")
        @Override
        public Client getById(@RequestParam("id") String id);

        @RequestMapping(value="/getAuthoritysByKhdid",method=RequestMethod.GET)
        @CacheableReadOnly(key = com.hlg.fcf.cache.Constants.ClientKey.getAuthoritysByKhdid_spel)
        @Override
        public Map getAuthoritysByKhdid(@RequestParam("khdid") String khdid);
    }

}
