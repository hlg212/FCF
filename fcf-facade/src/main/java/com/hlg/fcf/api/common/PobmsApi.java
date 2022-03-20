package com.hlg.fcf.api.common;

import com.hlg.fcf.api.Constants;
import com.hlg.fcf.model.basic.PobmsInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 
 * @author wuwei
 * @date 2019年9月9日
 */
@FeignClient(contextId = Constants.ApiContextId.PobmsApi,name=Constants.APP_APIGATEWAY_BASIC, url=Constants.AppFeignUrl.APP_APPLICATION_URL)
@RequestMapping(Constants.ApiMapping.PobmsApi)
@ConditionalOnExpression("false")
public interface PobmsApi {
	@RequestMapping(value="/getPobmss",method=RequestMethod.GET)
	public List<PobmsInfo> getPobmss();
	
}
