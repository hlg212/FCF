
package io.hlg212.fcf.event.cache;


import io.hlg212.fcf.annotation.RemoteEventAnnotation;
import io.hlg212.fcf.event.Constants;
import io.hlg212.fcf.event.RemoteEvent;
import lombok.Data;

public interface CacheEvent {

    public String getCacheName();

}