
package com.hlg.fcf.event.cache;


import com.hlg.fcf.annotation.RemoteEventAnnotation;
import com.hlg.fcf.event.Constants;
import com.hlg.fcf.event.RemoteEvent;
import lombok.Data;

public interface CacheEvent {

    public String getCacheName();

}