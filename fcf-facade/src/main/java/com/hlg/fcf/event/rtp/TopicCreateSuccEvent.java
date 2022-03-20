package com.hlg.fcf.event.rtp;

import com.hlg.fcf.annotation.RemoteEventAnnotation;
import com.hlg.fcf.event.BaseEvent;
import com.hlg.fcf.event.Constants;
import com.hlg.fcf.event.RemoteEvent;
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
