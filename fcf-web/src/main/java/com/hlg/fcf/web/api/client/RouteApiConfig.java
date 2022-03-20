package com.hlg.fcf.web.api.client;

import com.hlg.fcf.annotation.CacheableReadOnly;
import com.hlg.fcf.api.Constants;
import com.hlg.fcf.model.ga.Route;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Configuration
class RouteApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.RouteApi,name=Constants.APP_APIGATEWAY_ADMIN,path =Constants.APP_ADMIN_PATH,url =Constants.AppFeignUrl.APP_ADMIN)
    @CacheConfig(cacheNames = com.hlg.fcf.cache.Constants.Route)
    public interface RouteApi extends com.hlg.fcf.api.RouteApi<Route> {

        @RequestMapping(value="/getAllRoutes",method=RequestMethod.GET)
        @CacheableReadOnly(key =  com.hlg.fcf.cache.Constants.RouteKey.getAllRoutes_spel)
        @Override
        public List<Route> getAllRoutes();

    }
}
