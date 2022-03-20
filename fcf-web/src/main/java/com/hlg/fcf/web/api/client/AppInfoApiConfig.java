package com.hlg.fcf.web.api.client;

import com.hlg.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author wuwei
 * @date 2019年9月30日
 */
@Configuration
class AppInfoApiConfig {
	@FeignClient(contextId = Constants.ApiContextId.AppInfoApi, name = Constants.APP_APIGATEWAY_BASIC, /*path = Constants.APP_APPLICATION_NAME,*/ url=Constants.AppFeignUrl.APP_APPLICATION_URL)
	public interface AppInfoApi extends com.hlg.fcf.api.AppInfoApi {

	}
}
