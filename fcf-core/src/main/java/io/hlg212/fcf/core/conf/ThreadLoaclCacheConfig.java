package io.hlg212.fcf.core.conf;

import io.hlg212.fcf.annotation.CacheConditional;
import io.hlg212.fcf.cache.Constants;
import io.hlg212.fcf.util.LockHelper;
import io.hlg212.fcf.util.ThreadLocalHelper;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.cache.support.NullValue;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Callable;

/**
 * 提供线程级别的缓存支持。
 * 一些调用比较频繁的接口，可使用该种方式进行。
 * 比如： 获取当前登录用户
 * <p>
 * ClassName: ThreadLoaclCacheConfig
 * date: 2019年6月26日 上午8:50:16
 *
 * @author huangligui
 */
@Configuration
@CacheConditional
public class ThreadLoaclCacheConfig {


    @Bean(Constants.CacheManager.ThreadLocalCacheManager)
    public ThreadLoaclCacheManager threadLoaclCacheManager() {
        return new ThreadLoaclCacheManager();
    }

    private class ThreadLoaclCacheManager extends SimpleCacheManager {
        @Override
        protected Cache getMissingCache(String name) {
            return new ThreadLoaclCache(name);
        }
    }

    private class ThreadLoaclCache extends AbstractValueAdaptingCache {
        private String name = null;
        private static final String THREAD_LOCAL_CACHE = "THREAD_LOCAL_CACHE";

        public ThreadLoaclCache(String name) {
            super(true);
            this.name = name;
        }

        @Override
        protected Object lookup(Object o) {
            ConcurrentMapCache cache = getHolderCache();
            return cache.getNativeCache().get(o);
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public Object getNativeCache() {
            return this;
        }

        @Override
        public <T> T get(Object key, Callable<T> valueLoader) {
            Cache cache = getHolderCache();
            return cache.get(key, valueLoader);
        }

        @Override
        public void put(Object o, Object o1) {
            Cache cache = getHolderCache();

            cache.put(o, o1);
        }

        private ConcurrentMapCache getHolderCache() {
            String cacheKey = getCacheKey(this.name);
            ConcurrentMapCache cache = ThreadLocalHelper.get(cacheKey);
            if (cache == null) {
                LockHelper.lock(cacheKey);
                try {
                    if (cache == null) {
                        cache = new ConcurrentMapCache(name);
                        ThreadLocalHelper.set(cacheKey, cache);
                    }
                } catch (Exception e) {
                } finally {
                    LockHelper.unlock(cacheKey);
                }
            }
            return cache;
        }

        @Override
        public ValueWrapper putIfAbsent(Object o, Object o1) {
            Cache cache = getHolderCache();
            return cache.putIfAbsent(o, o1);
        }

        @Override
        public void evict(Object o) {
            Cache cache = getHolderCache();
            cache.evict(o);
        }

        @Override
        public void clear() {
            Cache cache = getHolderCache();
            cache.clear();
        }

        private String getCacheKey(Object o) {
            return THREAD_LOCAL_CACHE + "-" + this.name;
        }
    }
}
