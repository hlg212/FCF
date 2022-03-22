package  io.github.hlg212.fcf.event;

import  io.github.hlg212.fcf.api.rtp.PartitionApi;
import  io.github.hlg212.fcf.event.rtp.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: frame-parent
 * @description: 前端推送消息进行分区
 * @author  huangligui
 * @create: 2019-02-25 15:54
 **/
@Order(100)
@Component
@Slf4j
public class PartitionEventHandler extends AnnotationEventTopicHandler {

    @Autowired
    private PartitionApi partitionApi;

    @Override
    public String eventHandle(RemoteEvent event) {

        if( event instanceof PartitionEvent ){
            String routeKey = super.eventHandle(event);
            String partition = null;
            String topic = ((PartitionEvent) event).getPartitionProVal();
            try{
                partition =  partitionApi.getPartition(topic);
              }catch (Exception e)
              {
                  partition = "1";
                  log.warn("topic:{} 获取不到分区信息，采用默认分区:{}",topic,partition);
              }
          return routeKey + "." + partition;
        }
        return null;
    }
}
