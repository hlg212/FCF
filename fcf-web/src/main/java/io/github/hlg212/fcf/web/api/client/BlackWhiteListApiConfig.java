package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.annotation.CacheRead;
import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.basic.BlackWhiteList;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Configuration
class BlackWhiteListApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.BlackWhiteListApi,name=Constants.ApiName.BlackWhiteListApi,path =Constants.ApiPath.BlackWhiteListApi,url =Constants.ApiUrl.BlackWhiteListApi)
    @CacheConfig(cacheNames =  io.github.hlg212.fcf.cache.Constants.BlackWhiteList)
    public interface BlackWhiteListApi extends  io.github.hlg212.fcf.api.BlackWhiteListApi<BlackWhiteList>{

        @RequestMapping(value="/getBlackWhiteList",method= RequestMethod.GET)
        @CacheRead(key =  io.github.hlg212.fcf.cache.Constants.BlackWhiteListKey.getBlackWhiteList_spel)
        @Override
        public List<BlackWhiteList> getBlackWhiteList();

        @RequestMapping(value="/getBlackWhiteListByAppCode",method=RequestMethod.GET)
        @CacheRead(key =  io.github.hlg212.fcf.cache.Constants.BlackWhiteListKey.getBlackWhiteListByAppCode_spel)
        @Override
        public List<BlackWhiteList> getBlackWhiteListByAppCode(@RequestParam("appCode") String appCode);

        @RequestMapping(value="/getBlackWhiteListByAccount",method=RequestMethod.GET)
        @CacheRead(key =  io.github.hlg212.fcf.cache.Constants.BlackWhiteListKey.getBlackWhiteListByAccount_spel)
        @Override
        public List<BlackWhiteList> getBlackWhiteListByAccount(@RequestParam("account") String account);
    }
}
