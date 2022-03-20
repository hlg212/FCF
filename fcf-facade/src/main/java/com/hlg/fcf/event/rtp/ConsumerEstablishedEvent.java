
package com.hlg.fcf.event.rtp;


import com.hlg.fcf.annotation.RemoteEventAnnotation;
import com.hlg.fcf.event.BaseEvent;
import com.hlg.fcf.event.Constants;
import com.hlg.fcf.event.RemoteEvent;
import lombok.Data;

@RemoteEventAnnotation(topic = Constants.Topic.FRAME)
@Data
public class ConsumerEstablishedEvent extends RemoteEvent {

    private String token;

    private String userId;

    private String subscribes;

    private String page;

}