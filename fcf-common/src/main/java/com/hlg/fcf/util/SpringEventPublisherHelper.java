package com.hlg.fcf.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringEventPublisherHelper implements ApplicationEventPublisherAware{
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private static SpringEventPublisherHelper _instance;

     private static SpringEventPublisherHelper getInstance()
    {
        if( _instance == null )
        {
            _instance = SpringUtils.getBean(SpringEventPublisherHelper.class);
        }
        return _instance;
    }


    public static void publish(Object event) {
        getInstance().applicationEventPublisher.publishEvent(event);
    }
}
