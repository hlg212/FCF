package  io.github.hlg212.fcf.api;

import  io.github.hlg212.fcf.annotation.CacheableReadOnly;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(contextId = Constants.ApiContextId.AnonymousResApi ,name=Constants.APP_APIGATEWAY_ADMIN,path =Constants.APP_ADMIN_PATH,url =Constants.AppFeignUrl.APP_ADMIN)
@RequestMapping(Constants.ApiMapping.AnonymousResApi)
@ConditionalOnExpression("false")
public interface AnonymousResApi {

    @RequestMapping(value="/getAllUrls",method=RequestMethod.GET)
    @CacheableReadOnly(key =  io.github.hlg212.fcf.cache.Constants.AnonymousResKey.getAllUrls_spel)
    public List<String> getAllUrls();

}
