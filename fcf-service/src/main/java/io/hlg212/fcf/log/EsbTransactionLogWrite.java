package io.hlg212.fcf.log;

import io.hlg212.fcf.annotation.TransactionLogConditional;
import io.hlg212.fcf.event.log.TransactionLogSaveEvent;
import io.hlg212.fcf.model.log.ITransactionLog;
import io.hlg212.fcf.util.EventPublisherHelper;
import org.springframework.stereotype.Component;


public class EsbTransactionLogWrite implements  TransactionLogWrite{
    
    @Override
    public void write(ITransactionLog log) {
        TransactionLogSaveEvent event = new TransactionLogSaveEvent();
        event.setTransactionLog(log);
        EventPublisherHelper.publish(event);
    }
}
