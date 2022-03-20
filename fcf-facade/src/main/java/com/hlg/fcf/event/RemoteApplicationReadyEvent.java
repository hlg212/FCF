package com.hlg.fcf.event;

import com.hlg.fcf.annotation.RemoteEventAnnotation;
import lombok.Data;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @program: frame-parent
 * @description:  服务启动事件,当服务启动时向 eventBus发送该事件
 * @author: huangligui
 * @create: 2019-01-23 09:16
 **/
@RemoteEventAnnotation(topic = Constants.Topic.FRAME)
@Data
public class RemoteApplicationReadyEvent extends RemoteEvent
{
    private String appName;
    private String appPort;
    private String appIpAddress;

    private String appVersion;
    private String appDescription;

    public RemoteApplicationReadyEvent()
    {
        super();
    }
}
