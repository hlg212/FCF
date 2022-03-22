package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.annotation.CacheableReadOnly;
import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.basic.App;
import  io.github.hlg212.fcf.model.basic.Dic;
import  io.github.hlg212.fcf.model.basic.IApp;
import  io.github.hlg212.fcf.model.basic.Res;
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
    @CacheConfig(cacheNames =  io.github.hlg212.fcf.cache.Constants.Auth)
    public interface AuthApi extends  io.github.hlg212.fcf.api.AuthApi{

        /**
         * 获得所有需要授权的资源
         *
         */
        @Override
        @RequestMapping(value="/getAllAuthRes",method=RequestMethod.GET)
        @CacheableReadOnly(key =  io.github.hlg212.fcf.cache.Constants.AuthKey.getAllAuthRes_spel)
        public List<Res> getAllAuthRes();

        /**
         *  获得权限资源列表
         * @param userId
         *
         */
        @Override
        @RequestMapping(value="/getMenuByUserId",method=RequestMethod.GET)
        public List<Res>  getMenuByUserId(@RequestParam("appCode") String appCode, @RequestParam("userId") String userId);

        /**
         *  获得用户所拥有的app
         * @param userId
         *
         */
        @RequestMapping(value="/getAppsByUserId",method=RequestMethod.GET)
        public List<App> getAppsByUserId(@RequestParam("userId") String userId, @RequestParam("type") String type);
    }

}
