package io.hlg212.fcf.api.common;

import java.util.Collection;
import java.util.List;

import io.hlg212.fcf.api.Constants;
import io.hlg212.fcf.model.PageInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.hlg212.fcf.model.basic.CacheDetail;
import io.hlg212.fcf.model.basic.CacheInfo;

/**
 * 
 * @author huangligui
 * @date 2019年1月11日
 */
@FeignClient(contextId = Constants.ApiContextId.CacheApi,name=Constants.APP_APIGATEWAY_BASIC, url=Constants.AppFeignUrl.APP_APPLICATION_URL)
@RequestMapping(Constants.ApiMapping.CacheApi)
@ConditionalOnExpression("false")
public interface CacheApi {

	final int DEFAULT_SCAN_COUNT = 10000;
	
	@RequestMapping(value="/getCacheInfoList", method=RequestMethod.GET)
	public List<CacheInfo> getCacheInfoList();
	
	@RequestMapping(value="/getCacheDetailByCacheName", method=RequestMethod.GET)
	public PageInfo<CacheDetail> getCacheDetailByCacheName(@RequestParam("cacheName") String cacheName, @RequestParam(name="key",required=false)String key, @RequestParam("pageNum")int pageNum, @RequestParam("pageSize")int pageSize);

	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public void remove(@RequestParam("cacheName") String cacheName,@RequestParam("key")String key);

	@RequestMapping(value="/refresh", method=RequestMethod.POST)
	public void refresh(@RequestParam("cacheName") String cacheName,@RequestParam("key")String key);

	@RequestMapping(value="/reload", method=RequestMethod.POST)
	public void reload(@RequestParam("cacheName")String cacheName);

	@RequestMapping(value="/del", method=RequestMethod.POST)
	public void del(@RequestParam("cacheName")String cacheName);

	@RequestMapping(value="/getSubscribedCacheEvent", method=RequestMethod.GET)
	public Collection<String> getSubscribedCacheEvents();
	
}
