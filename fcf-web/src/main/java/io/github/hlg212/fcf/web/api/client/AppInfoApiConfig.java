package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author wuwei
 * @date 2019年9月30日
 */
@Configuration
class AppInfoApiConfig {
	@FeignClient(contextId = Constants.ApiContextId.AppInfoApi,name=Constants.ApiName.AppInfoApi,url=Constants.AppFeignUrl.AppInfoApi)	public interface AppInfoApi extends  io.github.hlg212.fcf.api.AppInfoApi {

	}
}
