package  io.github.hlg212.fcf.model.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
public class TransactionSqlLog implements  ITransactionSqlLog {

    private static final long serialVersionUID = 3015056298202911471L;
    //事务id
    private String transactionId;

    //开始时间
    private Date startTime;

    // 用户
    private String userId;

    //用户中文名，冗余
    private String userName;

    private String appCode;

    private Collection<ISqlLog> sqllogs;


    @JsonProperty("sqllogs")
    public void setSqllogs(Collection<SqlLog> sqllogs){
        this.sqllogs = new ArrayList<>();
        if(sqllogs != null) {
        	this.sqllogs.addAll(sqllogs);
        }
    }

}
