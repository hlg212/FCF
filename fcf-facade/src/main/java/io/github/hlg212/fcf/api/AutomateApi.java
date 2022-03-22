package  io.github.hlg212.fcf.api;

import  io.github.hlg212.fcf.properties.AutomateProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(contextId = Constants.ApiContextId.AutomateApi ,name=Constants.APP_APIGATEWAY_BASIC,url =Constants.AppFeignUrl.APP_APPLICATION_URL)
@RequestMapping(Constants.ApiMapping.AutomateApi)
@ConditionalOnExpression("false")
public interface AutomateApi {

    @RequestMapping(value="/getAutomateConfig",method=RequestMethod.GET)
    public AutomateProperties getAutomateConfig();

}
