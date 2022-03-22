package  io.github.hlg212.fcf.core.cachex;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 缓存错误处理
 *
 * @author  huangligui
 * @create: 2019-03-01 13:23
 */
@Slf4j
public class ExCacheErrorHandler implements CacheErrorHandler{

	@Override
	public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
		log.error("handleCacheGetError exception!key:{},errorMsg:{}", key , exception.getMessage());
	}

	@Override
	public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
		log.error("handleCachePutError exception!key:{},errorMsg:{}", key , exception.getMessage());
	}

	@Override
	public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
		log.error("handleCacheEvictError exception!key:{},errorMsg:{}", key , exception.getMessage());
	}

	@Override
	public void handleCacheClearError(RuntimeException exception, Cache cache) {
		log.error("handleCacheClearError exception!errorMsg:{}", exception.getMessage());
	}

}
