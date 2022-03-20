package com.hlg.fcf.listener;

import com.hlg.fcf.event.cache.CacheRefreshEvent;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2019-01-23 10:16
 **/
public interface CacheRefreshEventListener {

    public void onEvent(CacheRefreshEvent event);

}
