package  io.github.hlg212.fcf.api;

import  io.github.hlg212.fcf.model.basic.IApp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = Constants.ApiContextId.AppApi,name=Constants.ApiName.AppApi,path =Constants.ApiPath.AppApi,url =Constants.AppFeignUrl.AppApi)
@RequestMapping(Constants.ApiMapping.AppApi)
@ConditionalOnExpression("false")
public interface AppApi<T extends IApp>{

    /**
     * 根据编号获得应用
     * @param code
     *
     */
    @RequestMapping(value="/getAppByCode",method=RequestMethod.GET)
    public <E extends IApp> E getAppByCode(@RequestParam("code") String code);

    @RequestMapping(value="/getById",method=RequestMethod.GET)
    public T getById(@RequestParam("id") String id);
}
