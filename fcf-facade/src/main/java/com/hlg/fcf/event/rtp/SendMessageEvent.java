package com.hlg.fcf.event.rtp;

import com.hlg.fcf.annotation.RemoteEventAnnotation;
import com.hlg.fcf.event.BaseEvent;
import com.hlg.fcf.event.Constants;
import com.hlg.fcf.event.RemoteEvent;
import com.hlg.fcf.model.rtp.Message;
import lombok.Data;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2019-01-29 10:42
 **/
@RemoteEventAnnotation(topic = Constants.Topic.RTP)
@Data
public class SendMessageEvent extends RemoteEvent implements BaseEvent,PartitionEvent {

    private String topicId;

    private String routeKey;

    private Message message;

    @Override
    public String getPartitionProVal() {
        return getTopicId();
    }

}
