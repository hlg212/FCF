package com.hlg.fcf.event.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hlg.fcf.annotation.RemoteEventAnnotation;
import com.hlg.fcf.event.Constants;
import com.hlg.fcf.event.RemoteEvent;
import com.hlg.fcf.model.log.ITransactionSqlLog;
import com.hlg.fcf.model.log.TransactionSqlLog;
import lombok.Data;

@Data
@RemoteEventAnnotation(topic = Constants.Topic.TRANSACTIONSQLLOG)
public class TransactionSqlLogSaveEvent extends RemoteEvent {
    private ITransactionSqlLog transactionSqlLog;

    public ITransactionSqlLog getTransactionSqlLog(){
        return transactionSqlLog;
    }
    @JsonProperty("transactionSqlLog")
    public void setTransactionSqlLog(TransactionSqlLog transactionSqlLog){
        this.transactionSqlLog = transactionSqlLog;
    }

    public void setTransactionSqlLog(ITransactionSqlLog transactionSqlLog){
        this.transactionSqlLog = transactionSqlLog;
    }

}
