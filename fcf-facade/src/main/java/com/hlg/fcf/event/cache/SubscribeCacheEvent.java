package com.hlg.fcf.event.cache;

import com.hlg.fcf.annotation.RemoteEventAnnotation;
import com.hlg.fcf.event.Constants;
import com.hlg.fcf.event.RemoteEvent;
import lombok.Data;

import java.util.Collection;

@RemoteEventAnnotation(topic = Constants.Topic.FRAME )
@Data
public class SubscribeCacheEvent extends RemoteEvent {

    private String appName;

    private String serviceId;

    private Collection<String> cacheEventNames;
}
