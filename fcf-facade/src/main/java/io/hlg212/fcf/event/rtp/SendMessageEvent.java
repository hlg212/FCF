package io.hlg212.fcf.event.rtp;

import io.hlg212.fcf.annotation.RemoteEventAnnotation;
import io.hlg212.fcf.event.BaseEvent;
import io.hlg212.fcf.event.Constants;
import io.hlg212.fcf.event.RemoteEvent;
import io.hlg212.fcf.model.rtp.Message;
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
