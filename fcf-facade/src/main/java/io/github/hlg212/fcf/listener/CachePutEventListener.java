package  io.github.hlg212.fcf.listener;

import  io.github.hlg212.fcf.event.cache.CachePutEvent;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author  huangligui
 * @create: 2019-01-23 10:16
 **/
public interface CachePutEventListener {

    public void onEvent(CachePutEvent event);
}
