package  io.github.hlg212.fcf.api;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(contextId = Constants.ApiContextId.AppInfoApi,name=Constants.ApiName.AppInfoApi,url=Constants.ApiUrl.AppInfoApi)
@RequestMapping(Constants.ApiMapping.AppInfoApi)
@ConditionalOnExpression("false")
public interface AppInfoApi {
	@RequestMapping(value="/info")
	public String getInfo();
	
}
