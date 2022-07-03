package  io.github.hlg212.fcf.api.common;

import io.github.hlg212.fcf.api.Constants;
import io.github.hlg212.fcf.model.basic.PoInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(contextId = Constants.ApiContextId.PoInfoApi,name=Constants.ApiName.PoInfoApi, url=Constants.ApiUrl.PoInfoApi)
@RequestMapping(Constants.ApiMapping.PoInfoApi)
@ConditionalOnExpression("false")
public interface PoInfoApi {
	@RequestMapping(value="/getPoInfos",method=RequestMethod.GET)
	public List<PoInfo> getPoInfos();
	
}
