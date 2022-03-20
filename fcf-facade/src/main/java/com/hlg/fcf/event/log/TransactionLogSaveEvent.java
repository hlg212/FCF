package com.hlg.fcf.event.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hlg.fcf.annotation.RemoteEventAnnotation;
import com.hlg.fcf.event.Constants;
import com.hlg.fcf.event.RemoteEvent;
import com.hlg.fcf.model.log.ITransactionLog;
import com.hlg.fcf.model.log.TransactionLog;
import lombok.Data;

@Data
@RemoteEventAnnotation(topic = Constants.Topic.TRANSACTIONLOG)
public class TransactionLogSaveEvent extends RemoteEvent {
    private ITransactionLog transactionLog;

    public ITransactionLog getTransactionLog(){
        return transactionLog;
    }
    @JsonProperty("transactionLog")
    public void setTransactionLog(TransactionLog transactionLog){
        this.transactionLog = transactionLog;
    }

    public void setTransactionLog(ITransactionLog transactionLog){
        this.transactionLog = transactionLog;
    }

}
