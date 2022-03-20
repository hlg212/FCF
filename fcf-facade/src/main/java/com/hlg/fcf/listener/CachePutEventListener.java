package com.hlg.fcf.listener;

import com.hlg.fcf.event.cache.CachePutEvent;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2019-01-23 10:16
 **/
public interface CachePutEventListener {

    public void onEvent(CachePutEvent event);
}
