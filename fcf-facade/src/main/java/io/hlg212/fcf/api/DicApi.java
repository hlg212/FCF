package io.hlg212.fcf.api;

import io.hlg212.fcf.annotation.CacheableReadOnly;
import io.hlg212.fcf.model.basic.IDic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(contextId = Constants.ApiContextId.DicApi,name=Constants.APP_APIGATEWAY_BASIC,path =Constants.APP_BASIC_PATH,url =Constants.AppFeignUrl.APP_BASIC)
@RequestMapping(Constants.ApiMapping.DicApi)
@ConditionalOnExpression("false")
/**
 * @author wuwei
 * @date 2018年11月20日
 */
public interface DicApi<T extends IDic> {

    @RequestMapping(value="/getAllDics",method=RequestMethod.GET)
    @CacheableReadOnly(key = io.hlg212.fcf.cache.Constants.DicKey.getAllDics_spel)
    public List<T> getAllDics(@RequestParam("appCode") String appCode);


    @RequestMapping(value="/getById",method=RequestMethod.GET)
    @CacheableReadOnly(key = "#p0")
    public T getById(@RequestParam("id") String id);
}
