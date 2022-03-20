package com.hlg.fcf.log;

import com.hlg.fcf.annotation.TransactionLogConditional;
import com.hlg.fcf.event.log.TransactionLogSaveEvent;
import com.hlg.fcf.model.log.ITransactionLog;
import com.hlg.fcf.util.EventPublisherHelper;
import org.springframework.stereotype.Component;


public class EsbTransactionLogWrite implements  TransactionLogWrite{
    
    @Override
    public void write(ITransactionLog log) {
        TransactionLogSaveEvent event = new TransactionLogSaveEvent();
        event.setTransactionLog(log);
        EventPublisherHelper.publish(event);
    }
}
