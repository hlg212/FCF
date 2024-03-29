
package  io.github.hlg212.fcf.model.log;

import  io.github.hlg212.fcf.ISerializable;

import java.util.Date;

public interface ITransactionLog extends ISerializable {

    public String getId();

    public String getUserId();

    public String getUserName();

    public Date getStartTime();

    public Date getEndTime();

    public Long getDuration();

    public String getOperationName();

    public String getAccessId();

    public String getTraceId();

    public String getState();

    public String getParams();

    public String getExceptionMess();

    public String getExceptionStack();

    public String getAppCode();
}
