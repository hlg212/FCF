package  io.github.hlg212.fcf.log;

import  io.github.hlg212.fcf.annotation.AccessLogConditional;
import  io.github.hlg212.fcf.event.log.AccessLogSaveEvent;
import  io.github.hlg212.fcf.model.log.IAccessLog;
import  io.github.hlg212.fcf.util.EventPublisherHelper;
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
