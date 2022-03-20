package com.hlg.fcf.cache;

import com.hlg.fcf.event.cache.*;
import com.hlg.fcf.listener.*;
import com.hlg.fcf.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 缓存事件监听
 * @author: huangligui
 * @create: 2019-01-23 10:29
 **/
@Slf4j
public class CacheListenerImpl implements CacheReloadEventListener,CacheRefreshEventListener,CachePutEventListener,CacheDelEventListener,CacheRemoveEventListener {

    public CacheListenerImpl(CacheHandler cacheHandler)
    {
        this.cacheHandler = cacheHandler;
    }
    private CacheHandler cacheHandler;

    @Override
    @EventListener
    public void onEvent(CacheReloadEvent event) {

        cacheHandler.onReload(event.getCacheName());
    }
    @Override
    @EventListener
    public void onEvent(CacheRefreshEvent event) {
            cacheHandler.onRefresh(event.getCacheName(),event.getKey());
    }

    @Override
    @EventListener
    public void onEvent(CachePutEvent event) {
        cacheHandler.onPut(event.getCacheName(),event.getKey(),event.getValue());
    }


    @Override
    @EventListener
    public void onEvent(CacheDelEvent event) {
        cacheHandler.onDel(event.getCacheName());
    }

    @Override
    @EventListener
    public void onEvent(CacheRemoveEvent event) {
        cacheHandler.onRemove(event.getCacheName(),event.getKey());
    }
}
