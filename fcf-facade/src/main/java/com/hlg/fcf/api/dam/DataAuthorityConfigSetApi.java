package com.hlg.fcf.api.dam;

import com.hlg.fcf.api.Constants;
import com.hlg.fcf.model.dam.IDataAuthorityConfigSet;
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

@FeignClient(contextId = Constants.ApiContextId.DataAuthorityConfigSetApi,name=Constants.APP_APIGATEWAY_DAM,path =Constants.APP_DAM_PATH,url =Constants.AppFeignUrl.APP_DAM)
@RequestMapping(Constants.ApiMapping.DataAuthorityConfigSetApi)
@ConditionalOnExpression("false")
public interface DataAuthorityConfigSetApi<T extends IDataAuthorityConfigSet>{


    @RequestMapping(value="/getDataAuthorityConfigSetByCode",method=RequestMethod.GET)
    public List<T> getDataAuthorityConfigSetByCode(@RequestParam("code") String code);

}
