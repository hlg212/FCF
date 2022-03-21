package io.hlg212.fcf.init;

import io.hlg212.fcf.annotation.EsbConditional;
import io.hlg212.fcf.event.RemoteApplicationReadyEvent;
import io.hlg212.fcf.properties.AppInfoProperties;
import io.hlg212.fcf.util.EventPublisherHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@EsbConditional
@EnableConfigurationProperties(AppInfoProperties.class)
@Slf4j
public class AppInit implements ApplicationListener<ApplicationReadyEvent> {

    private boolean flag = false;
    private boolean publishFlag = false;

    @Value("${spring.application.name}")
    private String appName;
    @Value("${server.port}")
    private String appPort;
    @Value("${spring.cloud.client.ip-address}")
    private String ipAddress;

    @Autowired
    private AppInfoProperties appInfoProperties;

    @Override
    @Async
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        if( flag ) {
//            if( !publishFlag )
//            {
        log.info("app ready， appName[{}] appPort[{}] ipAddress[{}] desc[{}]",appName,appPort,ipAddress,appInfoProperties.getDescription());
        RemoteApplicationReadyEvent e = new RemoteApplicationReadyEvent();
        e.setAppName(appName);
        e.setAppPort(appPort);
        e.setAppIpAddress(ipAddress);
        e.setAppDescription(appInfoProperties.getDescription());
        e.setAppVersion(appInfoProperties.getVersion());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        EventPublisherHelper.publish(e);
        publishFlag = true;
//            }
//        }
//        flag = true;
    }

}
