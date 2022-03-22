
package  io.github.hlg212.fcf.event.cache;


import  io.github.hlg212.fcf.annotation.RemoteEventAnnotation;
import  io.github.hlg212.fcf.event.Constants;
import  io.github.hlg212.fcf.event.RemoteEvent;
import lombok.Data;

@Data
@RemoteEventAnnotation(topic = Constants.Topic.CACHE)
public class CachePutEvent extends RemoteEvent implements CacheEvent{

    private String cacheName;

    private String key;

    private String value;

}