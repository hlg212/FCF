package  io.github.hlg212.fcf.core.cachex;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/**
 * 
 * @author huangligui
 * @date 2018年12月10日
 */
@Slf4j
public class ExCacheResolver extends AbstractCacheResolver{

	public ExCacheResolver(CacheManager cacheManager) throws ClassNotFoundException {
		super(cacheManager);
	}

	@Override
	protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
		Set<String> cacheNames = context.getOperation().getCacheNames();
		if(cacheNames != null && cacheNames.size() > 0) {
			return cacheNames;
		}
		Object target = context.getTarget();
		Class<?> targetClass = AopUtils.getTargetClass(target);
		CacheConfig cacheConfig = targetClass.getAnnotation(CacheConfig.class);
		if(cacheConfig == null) {
			log.trace("{}当前service未启用缓存", targetClass);
			return Arrays.asList(NoneCache.CACHE_NAME);
		}
		String[] cacheNamesArr = cacheConfig == null ? null : cacheConfig.cacheNames();
		if(cacheNamesArr != null && cacheNamesArr.length > 0) {
			return Arrays.asList(cacheNamesArr);
		}
//		if(!htcfService.equals(targetClass)) {
//			log.trace("{}当前service未启动缓存", targetClass);
//			return Arrays.asList(NoneCache.CACHE_NAME);
//		}
//		try {
//			Field baseDao = FieldUtils.getField(targetClass, "baseDao", true);
//			Object baseDaoType = baseDao.get(target);
//			Class<?> baseDaoClass = AopUtils.getTargetClass(baseDaoType);
//			String poName = baseDaoClass.getSimpleName().replaceAll("Dao$", "");
//			return Arrays.asList(poName);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
		return null;
	}
	
}
