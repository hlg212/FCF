/** 
 * Project Name:frame-web 
 * File Name:LongRunningResApiConfig.java 
 * Package Name: io.github.hlg212.fcf.web.api.client
 * Date:2020年3月30日下午12:18:39 
 * Copyright (c) 2020, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.web.api.client;

import java.util.List;
import java.util.Map;

import  io.github.hlg212.fcf.model.ga.ILongRunningRes;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import  io.github.hlg212.fcf.annotation.CacheRead;
import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.ga.LongRunningRes;

/** 
 * ClassName: LongRunningResApiConfig <br/> 
 * date: 2020年3月30日 下午12:18:39 <br/> 
 * @author pengdz 
 * @version 
 */
@Configuration
public class LongRunningResApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.LongRunningResApi,name=Constants.ApiName.LongRunningResApi,path =Constants.ApiPath.LongRunningResApi,url =Constants.AppFeignUrl.LongRunningResApi)
    @CacheConfig(cacheNames =  io.github.hlg212.fcf.cache.Constants.LongRunningRes)
    public interface LongRunningResApi extends  io.github.hlg212.fcf.api.LongRunningResApi<LongRunningRes> {

        @RequestMapping(value="/getAllLongRunningRes",method=RequestMethod.GET)
        @CacheRead(key =   io.github.hlg212.fcf.cache.Constants.LongRunningResKey.getAllLongRunningRes_spel)
        @Override
        public List<LongRunningRes> getAllLongRunningRes();

    }
}
