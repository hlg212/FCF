package com.hlg.fcf.core.conf;

import com.hlg.fcf.annotation.CacheConditional;
import com.hlg.fcf.cache.Constants;
import com.hlg.fcf.util.CacheNameHelper;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

/**
 *  提供本地缓存支持。
 *  使用 ConcurrentMap 存储
 *  一些调用比较频繁的接口，可以将缓存方式改为本地化
 *
 * ClassName: SimpleCacheConfig
 * date: 2019年6月26日 上午8:50:16
 *
 * @author huangligui
 */
@Configuration
@CacheConditional
public class SimpleCacheConfig {


    @Bean(Constants.CacheManager.SimpleCacheManager)
    public SimpleCacheManager simpleCacheManager()
    {
        return new LocalSimpleCacheManager();
    }

    private class LocalSimpleCacheManager extends  SimpleCacheManager
    {
        @Override
        protected Cache getMissingCache(String name) {
            return new ConcurrentMapCache(name);
        }

        @Override
        public Collection<String> getCacheNames(){
            return CacheNameHelper.getCacheTypeMap().get(Constants.CacheManager.SimpleCacheManager);
        }
    }
}
