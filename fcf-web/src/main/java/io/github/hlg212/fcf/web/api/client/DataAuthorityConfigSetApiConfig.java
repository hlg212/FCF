package io.github.hlg212.fcf.web.api.client;

import io.github.hlg212.fcf.api.Constants;
import io.github.hlg212.fcf.model.dam.DataAuthorityConfigSet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;


@Configuration
class DataAuthorityConfigSetApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.DataAuthorityConfigSetApi,name=Constants.ApiName.DataAuthorityConfigSetApi,path =Constants.ApiPath.DataAuthorityConfigSetApi,url =Constants.ApiUrl.DataAuthorityConfigSetApi)
    @RequestMapping(Constants.ApiMapping.DataAuthorityConfigSetApi)
    public interface DataAuthorityConfigSetApi extends io.github.hlg212.fcf.api.dam.DataAuthorityConfigSetApi<DataAuthorityConfigSet> {

    }

}
