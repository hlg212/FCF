package com.hlg.fcf.init;

import com.hlg.fcf.annotation.CacheConditional;
import com.hlg.fcf.annotation.EsbConditional;
import com.hlg.fcf.api.common.CacheApi;
import com.hlg.fcf.event.cache.SubscribeCacheEvent;
import com.hlg.fcf.util.EventPublisherHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@EsbConditional
@CacheConditional
@Slf4j
public class CacheEventSubscriInit implements ApplicationListener<ApplicationReadyEvent> {


    @Value("${spring.application.name}")
    private String appName;

    @Value("${hlg.cacheServiceId:${hlg.serviceId}}")
    private String serviceId;

    //@Autowired
    @Resource(name="frame.cacheController")
    private CacheApi cacheApi;

    private boolean flag = false;

    @Override
    @Async
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if( !flag ) {
            flag = true;
            SubscribeCacheEvent e = new SubscribeCacheEvent();
            e.setAppName(appName);
            e.setServiceId(serviceId);
            e.setCacheEventNames(cacheApi.getSubscribedCacheEvents());
            if( e.getCacheEventNames() == null || e.getCacheEventNames().isEmpty() )
            {
                return;
            }
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e1) {
                log.warn(e1.getMessage());
            }
            log.info(" subscribe caches [{}], serviceId [{}]", e.getCacheEventNames(), e.getServiceId());

            EventPublisherHelper.publish(e);
        }
    }

}
