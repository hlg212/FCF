package  io.github.hlg212.fcf.api.dam;

import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.dam.IDataAuthorityConfigSet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *
 * 数据权限相关配置接口
 *
 * @author huangligui
 * @date 2021年1月11日
 */

@FeignClient(contextId = Constants.ApiContextId.DataAuthorityConfigSetApi,name=Constants.ApiName.DataAuthorityConfigSetApi,path =Constants.ApiPath.DataAuthorityConfigSetApi,url =Constants.AppFeignUrl.DataAuthorityConfigSetApi)
@RequestMapping(Constants.ApiMapping.DataAuthorityConfigSetApi)
@ConditionalOnExpression("false")
public interface DataAuthorityConfigSetApi<T extends IDataAuthorityConfigSet>{


    @RequestMapping(value="/getDataAuthorityConfigSetByCode",method=RequestMethod.GET)
    public List<T> getDataAuthorityConfigSetByCode(@RequestParam("code") String code);

}
