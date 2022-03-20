package com.hlg.fcf.log;

import com.hlg.fcf.annotation.AccessLogConditional;
import com.hlg.fcf.event.log.AccessLogSaveEvent;
import com.hlg.fcf.model.log.IAccessLog;
import com.hlg.fcf.util.EventPublisherHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@AccessLogConditional
@Component
public class EsbAccessLogWrite implements  AccessLogWrite{

    @Override
    public void write(IAccessLog log) {
        AccessLogSaveEvent event = new AccessLogSaveEvent();
        event.setAccessLog(log);
        EventPublisherHelper.publish(event);
    }
}
