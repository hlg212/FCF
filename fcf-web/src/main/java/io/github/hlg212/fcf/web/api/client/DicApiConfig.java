package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.annotation.CacheRead;
import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.basic.Dict;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Configuration
class DicApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.DictApi,name=Constants.ApiName.DictApi,path =Constants.ApiPath.DictApi,url =Constants.AppFeignUrl.DictApi)
    @CacheConfig(cacheNames =  io.github.hlg212.fcf.cache.Constants.Dict)
    public interface DicApi extends  io.github.hlg212.fcf.api.DictApi<Dict>{

        @RequestMapping(value="/getAllDicts",method=RequestMethod.GET)
        @CacheRead(key =  io.github.hlg212.fcf.cache.Constants.DictKey.getAllDicts_spel)
        @Override
        public List<Dict> getAllDicts(@RequestParam("appCode") String appCode);


        @RequestMapping(value="/getById",method=RequestMethod.GET)
        @CacheRead(key = "#p0")
        @Override
        public Dict getById(@RequestParam("id") String id);
    }

}
