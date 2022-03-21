package io.hlg212.fcf.event.rtp;

import io.hlg212.fcf.annotation.RemoteEventAnnotation;
import io.hlg212.fcf.event.BaseEvent;
import io.hlg212.fcf.event.Constants;
import io.hlg212.fcf.event.RemoteEvent;
import lombok.Data;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2019-01-24 10:03
 **/
@RemoteEventAnnotation(topic = Constants.Topic.FRAME)
@Data
public class TopicCreateSuccEvent extends RemoteEvent implements BaseEvent {
    
}
