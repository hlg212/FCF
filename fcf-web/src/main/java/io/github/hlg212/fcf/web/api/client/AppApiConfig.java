package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.annotation.CacheableReadOnly;
import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.basic.App;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Configuration
class AppApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.AppApi,name=Constants.APP_APIGATEWAY_BASIC,path =Constants.APP_BASIC_PATH,url =Constants.AppFeignUrl.APP_BASIC)
    @CacheConfig(cacheNames =  io.github.hlg212.fcf.cache.Constants.App)
    public interface AppApi  extends  io.github.hlg212.fcf.api.AppApi<App>{


        /**
         * 根据编号获得应用
         * @param code
         *  
         */
        @RequestMapping(value="/getAppByCode",method=RequestMethod.GET)
        @CacheableReadOnly(key =  io.github.hlg212.fcf.cache.Constants.AppKey.getAppByCode_spel)
        @Override
        public App getAppByCode(@RequestParam("code") String code);

        @RequestMapping(value="/getById",method=RequestMethod.GET)
        @CacheableReadOnly(key = "#p0")
        @Override
        public App getById(@RequestParam("id") String id);
    }

}
