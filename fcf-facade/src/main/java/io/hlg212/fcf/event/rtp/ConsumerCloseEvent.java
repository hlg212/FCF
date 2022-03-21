
package io.hlg212.fcf.event.rtp;


import io.hlg212.fcf.annotation.RemoteEventAnnotation;
import io.hlg212.fcf.event.BaseEvent;
import io.hlg212.fcf.event.Constants;
import io.hlg212.fcf.event.RemoteEvent;
import lombok.Data;

@RemoteEventAnnotation(topic = Constants.Topic.FRAME)
@Data
public class ConsumerCloseEvent extends RemoteEvent{

    private String token;
    
    private String userId;

    private String subscribes;

    private String page;
}