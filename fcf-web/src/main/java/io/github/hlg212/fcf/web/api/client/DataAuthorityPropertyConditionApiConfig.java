package io.github.hlg212.fcf.web.api.client;

import io.github.hlg212.fcf.api.Constants;
import io.github.hlg212.fcf.model.dam.DataAuthorityPropertyCondition;
import io.github.hlg212.fcf.model.dam.DataAuthorityPropertyConditionValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Configuration
class DataAuthorityPropertyConditionApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.DataAuthorityPropertyConditionApi,name=Constants.ApiName.DataAuthorityPropertyConditionApi,path =Constants.ApiPath.DataAuthorityPropertyConditionApi,url =Constants.ApiUrl.DataAuthorityPropertyConditionApi)
    @RequestMapping(Constants.ApiMapping.DataAuthorityPropertyConditionApi)
    public interface DataAuthorityPropertyConditionApi extends io.github.hlg212.fcf.api.dam.DataAuthorityPropertyConditionApi<DataAuthorityPropertyCondition> {

        @Override
        @RequestMapping(value = "/getValue", method = RequestMethod.GET)
        public DataAuthorityPropertyConditionValue getValue(@RequestParam("id")String id, @RequestParam("optype") String optype);
    }

}
