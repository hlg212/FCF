package com.hlg.fcf.event;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @description: 事件总线定义
 * 所有的事件，通过总线输出
 * @author: huangligui
 * @create: 2019-01-30 11:34
 **/
public interface EventServiceBusClient {
    String OUTPUT = "eventServiceBusOutput";

    @Output(OUTPUT)
    MessageChannel eventServiceBusOutput();
}
