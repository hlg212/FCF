
package  io.github.hlg212.fcf.event.rtp;


import  io.github.hlg212.fcf.annotation.RemoteEventAnnotation;
import  io.github.hlg212.fcf.event.BaseEvent;
import  io.github.hlg212.fcf.event.Constants;
import  io.github.hlg212.fcf.event.RemoteEvent;
import lombok.Data;

@RemoteEventAnnotation(topic = Constants.Topic.FRAME)
@Data
public class ConsumerEstablishedEvent extends RemoteEvent {

    private String token;

    private String userId;

    private String subscribes;

    private String page;

}