
package io.hlg212.fcf.event.cache;


import io.hlg212.fcf.annotation.RemoteEventAnnotation;
import io.hlg212.fcf.event.Constants;
import io.hlg212.fcf.event.RemoteEvent;
import lombok.Data;

@Data
@RemoteEventAnnotation(topic = Constants.Topic.CACHE)
public class CacheReloadEvent extends RemoteEvent implements CacheEvent{

    private String cacheName;

}