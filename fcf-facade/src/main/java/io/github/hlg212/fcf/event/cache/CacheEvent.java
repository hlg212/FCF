
package  io.github.hlg212.fcf.event.cache;


import  io.github.hlg212.fcf.annotation.RemoteEventAnnotation;
import  io.github.hlg212.fcf.event.Constants;
import  io.github.hlg212.fcf.event.RemoteEvent;
import lombok.Data;

public interface CacheEvent {

    public String getCacheName();

}