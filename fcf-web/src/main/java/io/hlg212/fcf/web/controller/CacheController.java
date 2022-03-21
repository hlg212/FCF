package io.hlg212.fcf.web.controller;

import io.hlg212.fcf.annotation.CacheConditional;
import io.hlg212.fcf.api.common.CacheApi;
import io.hlg212.fcf.cache.CacheHandlerReg;
import io.hlg212.fcf.cache.Constants;
import io.hlg212.fcf.event.cache.CacheRefreshEvent;
import io.hlg212.fcf.event.cache.CacheReloadEvent;
import io.hlg212.fcf.model.PageInfo;
import io.hlg212.fcf.model.basic.CacheDetail;
import io.hlg212.fcf.model.basic.CacheInfo;
import io.hlg212.fcf.util.EventPublisherHelper;
import io.hlg212.fcf.util.SpringHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;

/**
 * 
 * @author huangligui
 * @date 2019年1月11日
 */
@RestController("frame.cacheController")
@CacheConditional
@Api(value="框架缓存控制器",tags={"框架缓存控制器"})
public class CacheController implements CacheApi{
	
	@Autowired
	@Lazy
	private RedisCommands redisCommands;
	@Autowired
	private CacheManager cacheManager;

	@Autowired
	@Qualifier(Constants.CacheManager.SimpleCacheManager)
	private CacheManager simpleCacheManager;

	private StringRedisTemplate redisTemplate;

	@ResponseBody
	@ApiOperation("根据cacheName获取cacheValue值")
	@ApiImplicitParams({
			@ApiImplicitParam(name="cacheName", value="cacheName", required=true, paramType="query"),
			@ApiImplicitParam(name="key", value="key", paramType="query"),
			@ApiImplicitParam(name="pageNum", value="当前页数", required=true, dataType="int",example = "1",paramType="query"),
			@ApiImplicitParam(name="pageSize", value="每页显示数量", required=true, dataType="int",example = "10",paramType="query")
	})
	@Override
	public PageInfo<CacheDetail> getCacheDetailByCacheName(String cacheName, String key, int pageNum, int pageSize) {
		PageInfo<CacheDetail>  pageInfo = new PageInfo();
		List result = new ArrayList();
		this.execute(conn -> {
			String matchKey = "*" + ( StringUtils.isEmpty(key) ? "" : key ) + "*";
			byte[] cacheKey = cacheName.getBytes();
			DataType type = conn.type(cacheKey);
			switch (type) {
			case HASH:
				int start  = 0;
				if( start >= 1 )
				{
					start  = (pageNum -1) * pageSize;
				}

				Cursor<Map.Entry<byte[], byte[]>> cursor = conn.hScan(cacheName.getBytes(), new ScanOptions.ScanOptionsBuilder().count(pageSize).match(matchKey).build());

				for( int i=0;cursor.hasNext();i++)
				{
					Map.Entry<byte[], byte[]> e = cursor.next();
					if( i>= start )
					{
						byte[] k = e.getKey();
						byte[] v = e.getValue();
						CacheDetail tempCacheDetail = new CacheDetail();
						tempCacheDetail.setKey(createCacheKey(new String(k)));
						tempCacheDetail.setSize(k.length);
						tempCacheDetail.setTtl(conn.ttl(k));
						tempCacheDetail.setValue(new String(v));
						result.add(tempCacheDetail);
					}

				}
			default:
				break;
			}
			return 1;
		});

		pageInfo.setList(result);
		pageInfo.setTotal(result.size());
		pageInfo.setPages((int)Math.ceil(result.size()/pageSize));
		pageInfo.setPageSize(pageSize);

		return pageInfo;
	}

	private String createCacheKey(String key)
	{
		int index = key.indexOf("::");

		if( index >0 )
		{
			key = key.substring(index + 2);
		}
		return key;
	}

	@Override
	@ApiOperation("根据cacheName移除某个缓存")
	@ApiImplicitParams({
			@ApiImplicitParam(name="cacheName", value="cacheName", required=true, paramType="query"),
			@ApiImplicitParam(name="key", value="key", required=true, paramType="query")
	})
	public void remove(String cacheName, String key) {
		cacheManager.getCache(cacheName).evict(key);
	}

	@Override
	@ApiOperation(value="根据cacheName刷新某个缓存",notes = "请注意，刷新操作必须要有实现支持，否则只会发出事件")
	@ApiImplicitParams({
			@ApiImplicitParam(name="cacheName", value="cacheName", required=true, paramType="query"),
			@ApiImplicitParam(name="key", value="key", required=true, paramType="query")
	})
	public void refresh(String cacheName, String key) {
		CacheRefreshEvent event = new CacheRefreshEvent();
		event.setCacheName(cacheName);
		event.setKey(key);
		EventPublisherHelper.publish(event);
	}

	@Override
	@ApiOperation(value="根据cacheName重新加载缓存",notes = "请注意，操作必须要有实现支持，否则只会发出事件")
	@ApiImplicitParams({
			@ApiImplicitParam(name="cacheName", value="cacheName", required=true, paramType="query")
	})
	public void reload(String cacheName) {
		CacheReloadEvent event = new CacheReloadEvent();
		event.setCacheName(cacheName);
		EventPublisherHelper.publish(event);
	}

	@Override
	@ApiOperation(value="根据cacheName清除缓存")
	@ApiImplicitParams({
			@ApiImplicitParam(name="cacheName", value="cacheName", required=true, paramType="query")
	})
	public void del(String cacheName) {
		cacheManager.getCache(cacheName).clear();
	}




	@Override
	@ApiOperation("获取系统缓存信息列表")
	public List<CacheInfo> getCacheInfoList() {
		return this.execute(redisConnection -> {
			List<CacheInfo> result = new ArrayList<>();
//			Set<String> cns = CacheNameHelper.getCahceNames();
//			Map<String, Set> cacheMap = CacheNameHelper.getCacheTypeMap();

			Set<String> redis = (Set) cacheManager.getCacheNames();
			if(redis != null){
				redis.forEach(key->{
					String cacheName = new String(key);
					DataType type = redisConnection.type(key.getBytes());
					CacheInfo cacheInfo = new CacheInfo();
					cacheInfo.setCacheName(cacheName);
					cacheInfo.setCacheCount(this.getCacheCount(redisConnection, cacheName, type));
					cacheInfo.setCacheSize(this.getCacheSize(redisConnection, cacheName, type));
					cacheInfo.setCacheType(Constants.CacheManager.DefCacheManager);
					result.add(cacheInfo);
				});
			}

			Set<String> simple = (Set) simpleCacheManager.getCacheNames();
			if(simple != null){
				simple.forEach(key->{
					String cacheName = new String(key);
					//DataType type = redisConnection.type(key.getBytes());
					CacheInfo cacheInfo = new CacheInfo();
					cacheInfo.setCacheName(cacheName);
					cacheInfo.setCacheCount(0L);
					cacheInfo.setCacheSize(0L);
					cacheInfo.setCacheType(Constants.CacheManager.SimpleCacheManager);
					result.add(cacheInfo);
				});
			}
			return result;
		});
	}

	@Override
	@ApiOperation(value="获取订阅的缓存事件")
	public Collection<String> getSubscribedCacheEvents() {
		Collection<CacheHandlerReg> coll = SpringHelper.getApplicationContext().getBeansOfType(CacheHandlerReg.class).values();
		Set<String> eventNames = new HashSet<>();
		for( CacheHandlerReg reg : coll )
		{
			eventNames.add(reg.getCacheName());
		}
		return eventNames;
	}

	protected Long getCacheCount(RedisCommands redisConnection, String key, DataType type) {
		byte[] keyBytes = key.getBytes();
		if(DataType.HASH.equals(type)) {
			return redisConnection.hLen(keyBytes);
		}
		return 1L;
	}
	
	protected Long getCacheSize(RedisCommands redisConnection, String key, DataType type) {
		byte[] keyBytes = key.getBytes();
		Long result = -1L;
		switch (type) {
		case HASH:
			result = Optional.ofNullable(redisConnection.hVals(keyBytes))
					.orElse(Collections.emptyList()).stream().mapToLong(b -> b.length).sum();
			break;
		case STRING:
			result = (long) redisConnection.get(keyBytes).length;
			break;
		case LIST:
			long len = redisConnection.lLen(keyBytes) - 1;
			result = redisConnection.lRange(keyBytes, 0, len).stream().mapToLong(b -> b.length).sum();
			break;
		default:
			break;
		}
		return result;
	}

	protected <T> T execute(Function<RedisCommands, T> callback) {
		return callback.apply(redisCommands);
	}




}
