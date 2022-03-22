package  io.github.hlg212.fcf.api;

import  io.github.hlg212.fcf.model.basic.IClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(contextId = Constants.ApiContextId.ClientApi,name=Constants.APP_APIGATEWAY_BASIC,path =Constants.APP_BASIC_PATH,url =Constants.AppFeignUrl.APP_BASIC)
@RequestMapping(Constants.ApiMapping.ClientApi)
@ConditionalOnExpression("false")
public interface ClientApi<T extends IClient> {
    @RequestMapping(value="/getById",method= RequestMethod.GET)
    public T getById(@RequestParam("id") String id);

    @RequestMapping(value="/getAuthoritysByKhdid",method=RequestMethod.GET)
    public Map getAuthoritysByKhdid(@RequestParam("khdid") String khdid);
}
