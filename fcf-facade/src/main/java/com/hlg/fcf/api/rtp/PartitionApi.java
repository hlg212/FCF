package com.hlg.fcf.api.rtp;

import com.hlg.fcf.annotation.CacheableReadOnly;
import com.hlg.fcf.api.Constants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = Constants.ApiContextId.PartitionApi,name=Constants.APP_APIGATEWAY_RTP,path =Constants.APP_RTP_PATH,url =Constants.AppFeignUrl.APP_RTP)
@RequestMapping(Constants.ApiMapping.PartitionApi)
@CacheConfig(cacheNames = "PartitionApi")
@ConditionalOnExpression("false")
public interface PartitionApi {

    @RequestMapping(value="/getPartition",method=RequestMethod.GET)
    @CacheableReadOnly(key = "'partition:'+#p0")
    public String getPartition(@RequestParam("topicId")String topicId);

}
