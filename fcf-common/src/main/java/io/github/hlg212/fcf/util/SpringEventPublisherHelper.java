package  io.github.hlg212.fcf.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;


/**
 * spring 事件发布工具
 *
 * @author  huangligui
 * @create: 2019-03-06 09:44
 **/
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
            _instance = SpringHelper.getBean(SpringEventPublisherHelper.class);
        }
        return _instance;
    }


    public static void publish(Object event) {
        getInstance().applicationEventPublisher.publishEvent(event);
    }
}
