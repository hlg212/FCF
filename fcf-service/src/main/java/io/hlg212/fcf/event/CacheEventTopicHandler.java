package io.hlg212.fcf.event;

import io.hlg212.fcf.event.cache.CacheEvent;
import io.hlg212.fcf.util.AppContextHelper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(99)
@Component
public class CacheEventTopicHandler extends   AnnotationEventTopicHandler {
    @Override
    public String eventHandle(RemoteEvent event) {
        if( event instanceof CacheEvent )
        {
            CacheEvent cacheEvent = (CacheEvent)event;
            return super.eventHandle(event) + "." + AppContextHelper.getAppCode() + "." + cacheEvent.getCacheName();
        }
        return null;
    }
}
