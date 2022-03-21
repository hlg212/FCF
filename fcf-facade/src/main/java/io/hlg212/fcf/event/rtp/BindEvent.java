package io.hlg212.fcf.event.rtp;

import io.hlg212.fcf.annotation.RemoteEventAnnotation;
import io.hlg212.fcf.event.BaseEvent;
import io.hlg212.fcf.event.Constants;
import io.hlg212.fcf.event.RemoteEvent;
import lombok.Data;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2019-01-24 10:03
 **/
@Data
@RemoteEventAnnotation(topic = Constants.Topic.RTP)
public class BindEvent extends RemoteEvent implements BaseEvent,PartitionEvent {

    private String topicId;
    private String destId;
    private String routeKey;

    @Override
    public String getPartitionProVal() {
        return getTopicId();
    }
}
