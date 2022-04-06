package  io.github.hlg212.fcf.api;

import  io.github.hlg212.fcf.annotation.CacheRead;
import  io.github.hlg212.fcf.model.basic.IDict;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(contextId = Constants.ApiContextId.DictApi,name=Constants.ApiName.DictApi,path =Constants.ApiPath.DictApi,url =Constants.AppFeignUrl.DictApi)
@RequestMapping(Constants.ApiMapping.DictApi)
@ConditionalOnExpression("false")
public interface DictApi<T extends IDict> {

    @RequestMapping(value="/getAllDics",method=RequestMethod.GET)
    @CacheRead(key =  io.github.hlg212.fcf.cache.Constants.DictKey.getAllDicts_spel)
    public List<T> getAllDicts(@RequestParam("appCode") String appCode);


    @RequestMapping(value="/getById",method=RequestMethod.GET)
    @CacheRead(key = "#p0")
    public T getById(@RequestParam("id") String id);
}
