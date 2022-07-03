package  io.github.hlg212.fcf.api;

import  io.github.hlg212.fcf.model.ga.IRoute;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(contextId = Constants.ApiContextId.RouteApi,name=Constants.ApiName.RouteApi,path =Constants.ApiPath.RouteApi,url =Constants.ApiUrl.RouteApi)
@RequestMapping(Constants.ApiMapping.RouteApi)
@ConditionalOnExpression("false")
public interface RouteApi<T extends IRoute> {

    @RequestMapping(value="/getAllRoutes",method=RequestMethod.GET)
    public List<T> getAllRoutes();

    @RequestMapping(value="/getById",method=RequestMethod.GET)
    public T getById(@RequestParam("id") String id);
}
