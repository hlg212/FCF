package com.hlg.fcf.event;

import com.hlg.fcf.annotation.EsbConditional;
import com.hlg.fcf.annotation.RemoteEventAnnotation;
import com.hlg.fcf.util.EventTopicHandleHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @description:  远程事件发布
 * @author: huangligui
 * @create: 2019-01-03 16:25
 **/
@Component("Frame.RemoteEventPublisher")
@EsbConditional
@Slf4j
public class EsbRemoteEventPublisher implements RemoteEventPublisher {

    @Autowired
    @Output(EventServiceBusClient.OUTPUT)
    private MessageChannel eventServiceBusOutput;

    @Override
    public void publish(RemoteEvent event) {

      //  eventServiceBusOutput.send(MessageBuilder.withPayload(pack).setHeader("routeKey","remoteEvent").build());
        // 获得 event 上的 RemoteEventAnnotation 注解
        //
        long b = System.currentTimeMillis();
        String routeKey = EventTopicHandleHelper.getTopicKey(event);
        eventServiceBusOutput.send(MessageBuilder.withPayload(event).setHeader("routeKey",routeKey).build());
        long e = System.currentTimeMillis();
        if( log.isDebugEnabled() )
        {
            log.debug("发送消息到[{}],总耗时[{}]", routeKey,e - b);
        }
    }
}
