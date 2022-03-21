/** 
 * Project Name:frame-facade 
 * File Name:LongRunningResApi.java 
 * Package Name:io.hlg212.fcf.api
 * Date:2020年3月28日上午9:20:33 
 * Copyright (c) 2020, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package io.hlg212.fcf.api;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.hlg212.fcf.model.ga.ILongRunningRes;

@FeignClient(contextId = Constants.ApiContextId.LongRunningResApi,name=Constants.APP_APIGATEWAY_ADMIN,path =Constants.APP_ADMIN_PATH,url =Constants.AppFeignUrl.APP_ADMIN)
@RequestMapping(Constants.ApiMapping.LongRunningResApi)
@ConditionalOnExpression("true")
public interface LongRunningResApi<T extends ILongRunningRes> {

    @RequestMapping(value="/getAllLongRunningRes",method=RequestMethod.GET)
    public List<T> getAllLongRunningRes();

}
