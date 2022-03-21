package io.hlg212.fcf.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @description:  本地事件发布
 * @author: huangligui
 * @create: 2019-01-03 16:25
 **/
@Component("Frame.SpringEventPublisher")
@Primary
public class SpringEventPublisher implements ApplicationEventPublisherAware,EventPublisher<BaseEvent> {
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(BaseEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
