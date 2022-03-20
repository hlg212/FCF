package com.hlg.fcf.log;

import com.hlg.fcf.event.log.TransactionSqlLogSaveEvent;
import com.hlg.fcf.model.log.ITransactionSqlLog;
import com.hlg.fcf.util.EventPublisherHelper;


public class EsbTransactionSqlLogWrite implements  TransactionSqlLogWrite{

    @Override
    public void write(ITransactionSqlLog log) {
        TransactionSqlLogSaveEvent event = new TransactionSqlLogSaveEvent();
        event.setTransactionSqlLog(log);
        EventPublisherHelper.publish(event);
    }
}
