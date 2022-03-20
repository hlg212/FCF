package com.hlg.fcf.core.cachex;

import com.alibaba.fastjson.JSON;
import com.hlg.fcf.event.cache.CacheDelEvent;
import com.hlg.fcf.event.cache.CachePutEvent;
import com.hlg.fcf.event.cache.CacheRemoveEvent;
import com.hlg.fcf.util.EventPublisherHelper;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2019-03-01 13:23
 **/
public class CacheWapper implements Cache {
    private Cache cache;

    public CacheWapper(Cache cache)
    {
        this.cache = cache;
    }


    @Override
    public String getName() {
        return cache.getName();
    }

    @Override
    public Object getNativeCache() {
        return cache.getNativeCache();
    }

    @Override
    public ValueWrapper get(Object key) {
        return cache.get(key);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return cache.get(key,type);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return cache.get(key,valueLoader);
    }

    @Override
    public void put(Object key, Object value) {
        cache.put(key,value);
        CachePutEvent  event = new CachePutEvent();
        event.setCacheName( getName() );
        event.setKey( String.valueOf(key) );

        if( value != null )
        {
            event.setValue( JSON.toJSONString(value) );
        }



        EventPublisherHelper.publish(event);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return cache.putIfAbsent(key,value);
    }

    @Override
    public void evict(Object key) {
        cache.evict(key);

        CacheRemoveEvent event = new CacheRemoveEvent();
        event.setCacheName( getName() );
        event.setKey( String.valueOf(key) );
        EventPublisherHelper.publish(event);
    }

    @Override
    public void clear() {

        cache.clear();
        CacheDelEvent  event = new CacheDelEvent();
        event.setCacheName(getName());
        EventPublisherHelper.publish(event);
    }
}
