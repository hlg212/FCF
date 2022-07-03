package  io.github.hlg212.fcf.api;

import  io.github.hlg212.fcf.properties.AutomateProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(contextId = Constants.ApiContextId.AutomateApi ,name=Constants.ApiName.AutomateApi,url =Constants.ApiUrl.AutomateApi)
@RequestMapping(Constants.ApiMapping.AutomateApi)
@ConditionalOnExpression("false")
public interface AutomateApi {

    @RequestMapping(value="/getAutomateConfig",method=RequestMethod.GET)
    public AutomateProperties getAutomateConfig();

}
