package io.hlg212.fcf.event;

import io.hlg212.fcf.annotation.RemoteEventAnnotation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: frame-parent
 * @description: 从注解上面获取事件的topic
 * @author: huangligui
 * @create: 2019-02-25 15:54
 **/
@Order
@Component
public class AnnotationEventTopicHandler implements  RemoteEventTopicHandler {
    @Override
    public String eventHandle(RemoteEvent event) {
        RemoteEventAnnotation annotation = event.getClass().getAnnotation(RemoteEventAnnotation.class);
        String routeKey = annotation.topic() + "." + event.getClass().getSimpleName();
        return routeKey;
    }
}
