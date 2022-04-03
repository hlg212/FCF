package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.annotation.CacheableReadOnly;
import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.ga.Route;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Configuration
class RouteApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.RouteApi,name=Constants.ApiName.RouteApi,path =Constants.ApiPath.RouteApi,url =Constants.AppFeignUrl.RouteApi)
    @CacheConfig(cacheNames =  io.github.hlg212.fcf.cache.Constants.Route)
    public interface RouteApi extends  io.github.hlg212.fcf.api.RouteApi<Route> {

        @RequestMapping(value="/getAllRoutes",method=RequestMethod.GET)
        @CacheableReadOnly(key =   io.github.hlg212.fcf.cache.Constants.RouteKey.getAllRoutes_spel)
        @Override
        public List<Route> getAllRoutes();

    }
}
