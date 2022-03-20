package com.hlg.fcf.conf;

import com.hlg.fcf.annotation.EsbConditional;
import com.hlg.fcf.event.EventServiceBusCacheClient;
import com.hlg.fcf.event.EventServiceBusClient;
import com.hlg.fcf.event.EventServiceBusFrameClient;
import com.hlg.fcf.event.RemoteEvent;
import com.hlg.fcf.util.EventPublisherHelper;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;

/**
 * @description:  事件总线配置
 * @author: huangligui
 * @create: 2019-01-03 16:25
 **/
@Configuration
@EnableBinding({EventServiceBusClient.class,EventServiceBusFrameClient.class,EventServiceBusCacheClient.class})
@RemoteApplicationEventScan({"${hlg.fcf.basePackage}",
        "${hlg.package.event[0]:${hlg.default.package.basePackage[0]:o000oo000o}}",
        "${hlg.package.event[1]:${hlg.default.package.basePackage[1]:o000oo000o}}",
        "${hlg.package.event[2]:${hlg.default.package.basePackage[2]:o000oo000o}}",
        "${hlg.package.event[3]:${hlg.default.package.basePackage[3]:o000oo000o}}",
        "${hlg.package.event[4]:${hlg.default.package.basePackage[4]:o000oo000o}}",
        "${hlg.package.event[5]:${hlg.default.package.basePackage[5]:o000oo000o}}",
        "${hlg.package.event[6]:${hlg.default.package.basePackage[6]:o000oo000o}}",
        "${hlg.package.event[7]:${hlg.default.package.basePackage[7]:o000oo000o}}",
        "${hlg.package.event[8]:${hlg.default.package.basePackage[8]:o000oo000o}}",
        "${hlg.package.event[9]:${hlg.default.package.basePackage[9]:o000oo000o}}",
        "${hlg.package.event[10]:${hlg.default.package.basePackage[10]:o000oo000o}}",
        "${hlg.package.event[11]:${hlg.default.package.basePackage[11]:o000oo000o}}",
        "${hlg.package.event[12]:${hlg.default.package.basePackage[12]:o000oo000o}}",
        "${hlg.package.event[13]:${hlg.default.package.basePackage[13]:o000oo000o}}",
        "${hlg.package.event[14]:${hlg.default.package.basePackage[14]:o000oo000o}}",
        "${hlg.package.event[15]:${hlg.default.package.basePackage[15]:o000oo000o}}",
        "${hlg.package.event[16]:${hlg.default.package.basePackage[16]:o000oo000o}}",
        "${hlg.package.event[17]:${hlg.default.package.basePackage[17]:o000oo000o}}"
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
