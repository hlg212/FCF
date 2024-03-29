package  io.github.hlg212.fcf.log;

import  io.github.hlg212.fcf.event.log.TransactionSqlLogSaveEvent;
import  io.github.hlg212.fcf.model.log.ITransactionSqlLog;
import  io.github.hlg212.fcf.util.EventPublisherHelper;


public class EsbTransactionSqlLogWrite implements  TransactionSqlLogWrite{

    @Override
    public void write(ITransactionSqlLog log) {
        TransactionSqlLogSaveEvent event = new TransactionSqlLogSaveEvent();
        event.setTransactionSqlLog(log);
        EventPublisherHelper.publish(event);
    }
}
