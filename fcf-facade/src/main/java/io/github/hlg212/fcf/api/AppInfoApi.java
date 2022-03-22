package  io.github.hlg212.fcf.api;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author wuwei
 * @date 2019年9月30日
 */
@FeignClient(contextId = Constants.ApiContextId.AppInfoApi,name=Constants.APP_APIGATEWAY_BASIC,/*path=Constants.APP_APPLICATION_NAME,*/ url=Constants.AppFeignUrl.APP_APPLICATION_URL)
@RequestMapping("/actuator")
@ConditionalOnExpression("false")
public interface AppInfoApi {
	@RequestMapping(value="/info")
	public String getInfo();
	
}
