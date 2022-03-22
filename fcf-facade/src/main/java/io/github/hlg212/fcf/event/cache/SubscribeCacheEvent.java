package  io.github.hlg212.fcf.event.cache;

import  io.github.hlg212.fcf.annotation.RemoteEventAnnotation;
import  io.github.hlg212.fcf.event.Constants;
import  io.github.hlg212.fcf.event.RemoteEvent;
import lombok.Data;

import java.util.Collection;

@RemoteEventAnnotation(topic = Constants.Topic.FRAME )
@Data
public class SubscribeCacheEvent extends RemoteEvent {

    private String appName;

    private String serviceId;

    private Collection<String> cacheEventNames;
}
