package com.hlg.fcf.event.rtp;

import com.hlg.fcf.annotation.RemoteEventAnnotation;
import com.hlg.fcf.event.BaseEvent;
import com.hlg.fcf.event.Constants;
import com.hlg.fcf.event.RemoteEvent;
import lombok.Data;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2019-01-24 10:03
 **/
@Data
@RemoteEventAnnotation(topic = Constants.Topic.RTP)
public class UnBindEvent extends RemoteEvent implements BaseEvent,PartitionEvent {

    private String topicId;
    private String destId;
    private String routeKey;

    @Override
    public String getPartitionProVal() {
        return getTopicId();
    }
}
