package  io.github.hlg212.fcf.event.rtp;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import  io.github.hlg212.fcf.annotation.RemoteEventAnnotation;
import  io.github.hlg212.fcf.event.BaseEvent;
import  io.github.hlg212.fcf.event.Constants;
import  io.github.hlg212.fcf.event.RemoteEvent;
import  io.github.hlg212.fcf.model.rtp.ITopic;
import  io.github.hlg212.fcf.model.rtp.Topic;
import lombok.Data;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author  huangligui
 * @create: 2019-01-24 10:03
 **/
@RemoteEventAnnotation(topic = Constants.Topic.RTP)
public class TopicCreateEvent extends RemoteEvent implements BaseEvent,PartitionEvent {

    private ITopic topic;

    public ITopic getTopic(){
        return topic;
    }
    @JsonProperty("topic")
    public void setTopic(Topic topic){
        this.topic = topic;
    }

    public void setTopic(ITopic topic){
       this.topic = topic;
    }

    @Override
    public String getPartitionProVal() {
        return getTopic().getId();
    }
}
