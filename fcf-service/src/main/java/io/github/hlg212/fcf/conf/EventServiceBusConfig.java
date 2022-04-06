package  io.github.hlg212.fcf.conf;

import  io.github.hlg212.fcf.annotation.EsbConditional;
import  io.github.hlg212.fcf.event.EventServiceBusCacheClient;
import  io.github.hlg212.fcf.event.EventServiceBusClient;
import  io.github.hlg212.fcf.event.EventServiceBusFrameClient;
import  io.github.hlg212.fcf.event.RemoteEvent;
import  io.github.hlg212.fcf.util.EventPublisherHelper;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;

/**
 * @description:  事件总线配置
 * @author  huangligui
 * @create: 2019-01-03 16:25
 **/
@Configuration
@EnableBinding({EventServiceBusClient.class,EventServiceBusFrameClient.class,EventServiceBusCacheClient.class})
@RemoteApplicationEventScan({"${fcf.frame.basePackage}",
        "${fcf.package.event[0]:${fcf.default.package.basePackage[0]:o000oo000o}}",
        "${fcf.package.event[1]:${fcf.default.package.basePackage[1]:o000oo000o}}",
        "${fcf.package.event[2]:${fcf.default.package.basePackage[2]:o000oo000o}}",
        "${fcf.package.event[3]:${fcf.default.package.basePackage[3]:o000oo000o}}",
        "${fcf.package.event[4]:${fcf.default.package.basePackage[4]:o000oo000o}}",
        "${fcf.package.event[5]:${fcf.default.package.basePackage[5]:o000oo000o}}",
        "${fcf.package.event[6]:${fcf.default.package.basePackage[6]:o000oo000o}}",
        "${fcf.package.event[7]:${fcf.default.package.basePackage[7]:o000oo000o}}",
        "${fcf.package.event[8]:${fcf.default.package.basePackage[8]:o000oo000o}}",
        "${fcf.package.event[9]:${fcf.default.package.basePackage[9]:o000oo000o}}",
        "${fcf.package.event[10]:${fcf.default.package.basePackage[10]:o000oo000o}}",
        "${fcf.package.event[11]:${fcf.default.package.basePackage[11]:o000oo000o}}",
        "${fcf.package.event[12]:${fcf.default.package.basePackage[12]:o000oo000o}}",
        "${fcf.package.event[13]:${fcf.default.package.basePackage[13]:o000oo000o}}",
        "${fcf.package.event[14]:${fcf.default.package.basePackage[14]:o000oo000o}}",
        "${fcf.package.event[15]:${fcf.default.package.basePackage[15]:o000oo000o}}",
        "${fcf.package.event[16]:${fcf.default.package.basePackage[16]:o000oo000o}}",
        "${fcf.package.event[17]:${fcf.default.package.basePackage[17]:o000oo000o}}"
})
@EsbConditional
public class EventServiceBusConfig  {


    @StreamListener(EventServiceBusFrameClient.INPUT)
    public void acceptFrameRemote(RemoteEvent event) {
        EventPublisherHelper.publish(event);
    }

    @StreamListener(EventServiceBusCacheClient.INPUT)
    public void acceptCacheRemote(RemoteEvent event) {
        EventPublisherHelper.publish(event);
    }

}
