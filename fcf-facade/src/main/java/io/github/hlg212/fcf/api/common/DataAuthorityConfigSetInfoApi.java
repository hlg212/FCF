package  io.github.hlg212.fcf.api.common;

import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.dam.DataAuthorityConfigSetInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 *
 * 暴露出的数据权限信息接口
 * 
 * @author huangligui
 * @date 2021年1月11日
 */
@FeignClient(contextId = Constants.ApiContextId.DataAuthorityConfigSetInfoApi,name=Constants.ApiName.DataAuthorityConfigSetInfoApi, url=Constants.AppFeignUrl.DataAuthorityConfigSetInfoApi)
@RequestMapping(Constants.ApiMapping.DataAuthorityConfigSetInfoApi)
@ConditionalOnExpression("false")
public interface DataAuthorityConfigSetInfoApi {
	@RequestMapping(value="/geInfo",method=RequestMethod.GET)
	public List<DataAuthorityConfigSetInfo> geInfo();
	
}
