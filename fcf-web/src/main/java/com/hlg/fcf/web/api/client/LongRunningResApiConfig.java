/** 
 * Project Name:frame-web 
 * File Name:LongRunningResApiConfig.java 
 * Package Name:com.hlg.fcf.web.api.client 
 * Date:2020年3月30日下午12:18:39 
 * Copyright (c) 2020, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package com.hlg.fcf.web.api.client;

import java.util.List;
import java.util.Map;

import com.hlg.fcf.model.ga.ILongRunningRes;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hlg.fcf.annotation.CacheableReadOnly;
import com.hlg.fcf.api.Constants;
import com.hlg.fcf.model.ga.LongRunningRes;

/** 
 * ClassName: LongRunningResApiConfig <br/> 
 * date: 2020年3月30日 下午12:18:39 <br/> 
 * @author pengdz 
 * @version 
 */
@Configuration
public class LongRunningResApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.LongRunningResApi,name=Constants.APP_APIGATEWAY_ADMIN,path =Constants.APP_ADMIN_PATH,url =Constants.AppFeignUrl.APP_ADMIN)
    @CacheConfig(cacheNames = com.hlg.fcf.cache.Constants.LongRunningRes)
    public interface LongRunningResApi extends com.hlg.fcf.api.LongRunningResApi<LongRunningRes> {

        @RequestMapping(value="/getAllLongRunningRes",method=RequestMethod.GET)
        @CacheableReadOnly(key =  com.hlg.fcf.cache.Constants.LongRunningResKey.getAllLongRunningRes_spel)
        @Override
        public List<LongRunningRes> getAllLongRunningRes();

    }
}
