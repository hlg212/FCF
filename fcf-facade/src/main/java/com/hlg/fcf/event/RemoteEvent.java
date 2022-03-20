package com.hlg.fcf.event;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @program: frame-parent
 * @description:  远程事件定义
 * 基于 spring bus
 * 继承该类的事件会被发送到esb中，其它服务可获取到
 * @author: huangligui
 * @create: 2019-01-23 09:16
 **/
public class RemoteEvent extends RemoteApplicationEvent implements BaseEvent {
    private static final Object TRANSIENT_SOURCE = new Object();
    private String originService;

    public RemoteEvent()
    {
        super();
    }

    public RemoteEvent(String destinationService) {
        super(TRANSIENT_SOURCE,null,destinationService);
    }


    @Override
    public String getOriginService() {
        if( StringUtils.isNotEmpty( this.originService ) )
        {
            return originService;
        }
       return super.getOriginService();
    }

    public void setOriginService(String originService) {
        this.originService = originService;
    }
}
