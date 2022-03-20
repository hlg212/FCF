package com.hlg.fcf.web.api.client;

import com.hlg.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class FileApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.FileApi,name = Constants.APP_APIGATEWAY_FILES,path = Constants.APP_FILES_PATH,url = Constants.AppFeignUrl.APP_FILES)
    public interface FileApi extends com.hlg.fcf.api.FileApi{
        /**
         * 下载文件
         */
    /*@Override
    @RequestMapping(value="/download")
    public File download(@RequestParam("cclj") String cclj);*/
    }

}
