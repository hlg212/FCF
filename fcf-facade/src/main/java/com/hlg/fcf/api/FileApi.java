package com.hlg.fcf.api;

import com.hlg.fcf.model.basic.IFile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = Constants.ApiContextId.FileApi,name=Constants.APP_APIGATEWAY_FILES,path =Constants.APP_FILES_PATH,url =Constants.AppFeignUrl.APP_FILES)
@RequestMapping(Constants.ApiMapping.FileApi)
@ConditionalOnExpression("false")
public interface FileApi{
    /**
     * 上传文件
     */
    @RequestMapping(value="/upload",method=RequestMethod.POST)
    public String upload(@RequestBody IFile iFile);

    /**
     * 下载文件
     */
    @RequestMapping(value="/download",method=RequestMethod.GET)
    public <E extends IFile> E download(@RequestParam("cclj") String cclj);

    /**
     * 删除文件
     */
    @RequestMapping(value="/delete",method=RequestMethod.GET)
    public void delete(@RequestParam("cclj") String cclj);
}
