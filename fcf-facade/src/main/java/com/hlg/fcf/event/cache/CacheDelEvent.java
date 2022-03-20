
package com.hlg.fcf.event.cache;


import com.hlg.fcf.annotation.RemoteEventAnnotation;
import com.hlg.fcf.event.Constants;
import com.hlg.fcf.event.RemoteEvent;
import lombok.Data;

@Data
@RemoteEventAnnotation(topic = Constants.Topic.CACHE)
public class CacheDelEvent extends RemoteEvent implements CacheEvent {

    private String cacheName;

}