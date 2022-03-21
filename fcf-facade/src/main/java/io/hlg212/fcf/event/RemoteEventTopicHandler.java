package io.hlg212.fcf.event;

/**
 * @program: frame-parent
 * @description: 事件传递的topic（routeKey）处理获取
 * @author: huangligui
 * @create: 2019-02-25 15:50
 **/
public interface RemoteEventTopicHandler {
    /**
     * 返回 该事件的 topic 串
     * @param event
     * @return
     */
    public String eventHandle(RemoteEvent event);
}
