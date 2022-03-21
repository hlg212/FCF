package io.hlg212.fcf.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**
 * @description: 框架层事件输入
 * 
 * @author: huangligui
 * @create: 2019-01-30 11:34
 **/
public interface EventServiceBusFrameClient {
    String INPUT = "eventServiceBusFrameInput";

    @Input(INPUT)
    MessageChannel eventServiceBusFrameInput();
}
