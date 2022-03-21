package io.hlg212.fcf.listener;

import io.hlg212.fcf.event.cache.CacheReloadEvent;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2019-01-23 10:16
 **/
public interface CacheReloadEventListener {

    public void onEvent(CacheReloadEvent event);
}
