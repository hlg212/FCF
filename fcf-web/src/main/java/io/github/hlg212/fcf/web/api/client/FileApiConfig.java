package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;


@Configuration
class FileApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.FileApi,name = Constants.APP_APIGATEWAY_FILES,path = Constants.APP_FILES_PATH,url = Constants.AppFeignUrl.APP_FILES)
    public interface FileApi extends  io.github.hlg212.fcf.api.FileApi{
        /**
         * 下载文件
         */
    /*@Override
    @RequestMapping(value="/download")
    public File download(@RequestParam("cclj") String cclj);*/
    }

}
