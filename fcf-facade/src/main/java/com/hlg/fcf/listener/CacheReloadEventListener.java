package com.hlg.fcf.listener;

import com.hlg.fcf.event.cache.CacheReloadEvent;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2019-01-23 10:16
 **/
public interface CacheReloadEventListener {

    public void onEvent(CacheReloadEvent event);
}
