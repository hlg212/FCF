package com.hlg.fcf.service.impl;

import com.hlg.fcf.api.LongRunningResApi;
import com.hlg.fcf.cache.Constants;
import com.hlg.fcf.model.ga.ILongRunningRes;
import com.hlg.fcf.service.LongRunningResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Iterator;
import java.util.List;

/**
 * @author hlg
 */
@Component
@CacheConfig(cacheNames = Constants.LongRunningRes,cacheManager = Constants.CacheManager.SimpleCacheManager)
public class LongRunningResServiceImpl implements LongRunningResService {

    @Autowired
    @Lazy
    private LongRunningResApi longRunningResApi;

    @Autowired
    @Lazy
    private LongRunningResServiceImpl self;

    private AntPathMatcher matcher = new AntPathMatcher();

    @Override
    @Cacheable(key = Constants.LongRunningResKey.getUriTimeout_spel,unless="#result == null")
    public Integer getUriTimeout(String uri) {
        List<ILongRunningRes> res = self.getAllLongRunningRes();
        Iterator<ILongRunningRes> iter = res.iterator();

        while( iter.hasNext() )
        {
            ILongRunningRes entry = iter.next();
            if( matcher.match(entry.getUri(),uri) )
            {
                return entry.getTimeout();
            }
        }
        return null;
    }

    @Cacheable(key = Constants.LongRunningResKey.getAllLongRunningRes_spel)
    @Override
    public List<ILongRunningRes>  getAllLongRunningRes()
    {
        return longRunningResApi.getAllLongRunningRes();
    }
}
