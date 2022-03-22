package  io.github.hlg212.fcf.event.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import  io.github.hlg212.fcf.annotation.RemoteEventAnnotation;
import  io.github.hlg212.fcf.event.Constants;
import  io.github.hlg212.fcf.event.RemoteEvent;
import  io.github.hlg212.fcf.model.log.ITransactionLog;
import  io.github.hlg212.fcf.model.log.TransactionLog;
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
