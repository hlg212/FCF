package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import io.github.hlg212.fcf.model.basic.File;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Configuration
class FileApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.FileApi,name=Constants.ApiName.FileApi,path =Constants.ApiPath.FileApi,url =Constants.AppFeignUrl.FileApi)
    public interface FileApi extends  io.github.hlg212.fcf.api.FileApi{
        /**
         * 下载文件
         */
        @Override
        @RequestMapping(value="/download")
        public File download(@RequestParam("path") String path);
    }

}
