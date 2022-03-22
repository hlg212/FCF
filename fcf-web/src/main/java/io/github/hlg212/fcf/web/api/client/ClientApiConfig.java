package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.annotation.CacheableReadOnly;
import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.basic.Client;
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
    @CacheConfig(cacheNames =  io.github.hlg212.fcf.cache.Constants.Client)
    public interface ClientApi extends  io.github.hlg212.fcf.api.ClientApi<Client>{

        @RequestMapping(value="/getById",method= RequestMethod.GET)
        @CacheableReadOnly(key = "#p0")
        @Override
        public Client getById(@RequestParam("id") String id);

        @RequestMapping(value="/getAuthoritysByKhdid",method=RequestMethod.GET)
        @CacheableReadOnly(key =  io.github.hlg212.fcf.cache.Constants.ClientKey.getAuthoritysByKhdid_spel)
        @Override
        public Map getAuthoritysByKhdid(@RequestParam("khdid") String khdid);
    }

}
