package com.hlg.fcf.api;

import com.hlg.fcf.model.ga.IRoute;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(contextId = Constants.ApiContextId.RouteApi,name=Constants.APP_APIGATEWAY_ADMIN,path =Constants.APP_ADMIN_PATH,url =Constants.AppFeignUrl.APP_ADMIN)
@RequestMapping(Constants.ApiMapping.RouteApi)
@ConditionalOnExpression("false")
public interface RouteApi<T extends IRoute> {

    @RequestMapping(value="/getAllRoutes",method=RequestMethod.GET)
    public List<T> getAllRoutes();

    @RequestMapping(value="/getById",method=RequestMethod.GET)
    public T getById(@RequestParam("id") String id);
}
