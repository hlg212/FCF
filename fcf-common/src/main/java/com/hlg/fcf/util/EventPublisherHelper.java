package com.hlg.fcf.util;

import com.hlg.fcf.event.BaseEvent;
import com.hlg.fcf.event.EventPublisher;
import com.hlg.fcf.event.RemoteEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventPublisherHelper {

    private static EventPublisherHelper _instance;

    @Autowired
    @Qualifier("Frame.SpringEventPublisher")
    private EventPublisher eventPublisher;

    @Autowired(required=false)
    @Qualifier("Frame.RemoteEventPublisher")
    private EventPublisher remoteEventPublisher;

    @Autowired(required=false)
    private ServiceMatcher serviceMatcher;

    @Value("${hlg.serviceId:}")
    private String serviceId;

    private static EventPublisherHelper getInstance()
    {
        if( _instance == null )
        {
            _instance = SpringUtils.getBean(EventPublisherHelper.class);
        }
        return _instance;
    }


    public static void publish(BaseEvent event)
    {
        if( event instanceof  RemoteEvent )
        {
            if( getInstance().remoteEventPublisher == null )
            {
                log.warn("esb被关闭,请检查配置 hlg.esb.enable ");
            }
            if( StringUtils.isEmpty(((RemoteEvent) event).getOriginService()) )
            {
                ((RemoteEvent) event).setOriginService(getInstance().serviceId);
                getInstance().remoteEventPublisher.publish(event);
            }
            else
            {
               if(  getInstance().serviceMatcher != null && getInstance().serviceMatcher.isForSelf((RemoteApplicationEvent) event)) {
                   ((RemoteEvent) event).setOriginService("-");
                   //if ( !getInstance().serviceMatcher.isFromSelf((RemoteApplicationEvent) event)) {
                       getInstance().eventPublisher.publish(event);
                  // }
               }
            }
        }else
        {
            getInstance().eventPublisher.publish(event);
        }
    }
}
