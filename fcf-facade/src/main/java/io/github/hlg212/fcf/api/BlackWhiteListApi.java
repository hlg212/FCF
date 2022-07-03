package  io.github.hlg212.fcf.api;

import  io.github.hlg212.fcf.annotation.CacheRead;
import  io.github.hlg212.fcf.model.basic.IBlackWhiteList;
import  io.github.hlg212.fcf.model.basic.IClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(contextId = Constants.ApiContextId.BlackWhiteListApi,name=Constants.ApiName.BlackWhiteListApi,path =Constants.ApiPath.BlackWhiteListApi,url =Constants.ApiUrl.BlackWhiteListApi)
@RequestMapping(Constants.ApiMapping.BlackWhiteListApi)
@ConditionalOnExpression("false")
public interface BlackWhiteListApi<T extends IBlackWhiteList> {
    @RequestMapping(value="/getBlackWhiteList",method= RequestMethod.GET)
    @CacheRead(key =  io.github.hlg212.fcf.cache.Constants.BlackWhiteListKey.getBlackWhiteList_spel)
    public List<T> getBlackWhiteList();

    @RequestMapping(value="/getBlackWhiteListByAppCode",method=RequestMethod.GET)
    @CacheRead(key =  io.github.hlg212.fcf.cache.Constants.BlackWhiteListKey.getBlackWhiteListByAppCode_spel)
    public List<T> getBlackWhiteListByAppCode(@RequestParam("appCode") String appCode);

    @RequestMapping(value="/getBlackWhiteListByAccount",method=RequestMethod.GET)
    @CacheRead(key =  io.github.hlg212.fcf.cache.Constants.BlackWhiteListKey.getBlackWhiteListByAccount_spel)
    public List<T> getBlackWhiteListByAccount(@RequestParam("account") String account);
}
