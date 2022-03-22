package  io.github.hlg212.fcf.event.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import  io.github.hlg212.fcf.annotation.RemoteEventAnnotation;
import  io.github.hlg212.fcf.event.Constants;
import  io.github.hlg212.fcf.event.RemoteEvent;
import  io.github.hlg212.fcf.model.log.ITransactionSqlLog;
import  io.github.hlg212.fcf.model.log.TransactionSqlLog;
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
