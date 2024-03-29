package  io.github.hlg212.fcf.api.rtp;

import  io.github.hlg212.fcf.annotation.CacheRead;
import  io.github.hlg212.fcf.api.Constants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = Constants.ApiContextId.PartitionApi,name=Constants.ApiName.PartitionApi,path =Constants.ApiPath.PartitionApi,url =Constants.AppFeignUrl.PartitionApi)
@RequestMapping(Constants.ApiMapping.PartitionApi)
@CacheConfig(cacheNames = "PartitionApi")
@ConditionalOnExpression("false")
public interface PartitionApi {

    @RequestMapping(value="/getPartition",method=RequestMethod.GET)
    @CacheRead(key = "'partition:'+#p0")
    public String getPartition(@RequestParam("topicId")String topicId);

}
