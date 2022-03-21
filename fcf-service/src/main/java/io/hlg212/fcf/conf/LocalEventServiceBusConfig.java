package io.hlg212.fcf.conf;

import io.hlg212.fcf.event.RemoteEvent;
import io.hlg212.fcf.event.RemoteEventPublisher;
import io.hlg212.fcf.util.EventTopicHandleHelper;
import io.hlg212.fcf.util.SpringEventPublisherHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @description:  事件总线配置
 * @author: huangligui
 * @create: 2019-01-03 16:25
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(value = "esb.local",prefix = "hlg")
public class LocalEventServiceBusConfig {


    @Bean("Frame.RemoteEventPublisher")
    @ConditionalOnMissingBean({RemoteEventPublisher.class})
    public RemoteEventPublisher localEsbRemoteEventPublisher()
    {
        return new LocalEsbRemoteEventPublisher();
    }

    public class LocalEsbRemoteEventPublisher implements RemoteEventPublisher {
        @Autowired
        @Qualifier("localOutEventChannel")
        private MessageChannel localEventChannel;


        @Override
        public void publish(RemoteEvent event) {

            String routeKey = EventTopicHandleHelper.getTopicKey(event);

            localEventChannel.send(MessageBuilder.withPayload(event).setHeader("routeKey",routeKey).build());
        }
    }

    @Bean("localOutEventChannel")
    public QueueChannel localEventChannel()
    {
        return  new QueueChannel();
    }

    @Bean(initMethod="start")
    public MessagePublishThread messagePublishThread( )
    {
        return new MessagePublishThread(localEventChannel());
    }
    
    private class MessagePublishThread extends Thread{

        private QueueChannel queueChannel;

        private MessagePublishThread(QueueChannel queueChannel)
        {
            this.queueChannel = queueChannel;
        }

        @Override
		public void run() {

            while( true )
            {
                try {
					Message<?> message = queueChannel.receive();
					SpringEventPublisherHelper.publish((RemoteEvent)message.getPayload());
				} catch (Exception e) {
					log.warn("事件回调处理失败", e);
				}
            }
		}
    	
    }
}
