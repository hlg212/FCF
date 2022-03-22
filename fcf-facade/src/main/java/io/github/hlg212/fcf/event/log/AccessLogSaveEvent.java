package  io.github.hlg212.fcf.event.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import  io.github.hlg212.fcf.annotation.RemoteEventAnnotation;
import  io.github.hlg212.fcf.event.Constants;
import  io.github.hlg212.fcf.event.RemoteEvent;
import  io.github.hlg212.fcf.model.log.AccessLog;
import  io.github.hlg212.fcf.model.log.IAccessLog;
import lombok.Data;

@Data
@RemoteEventAnnotation(topic = Constants.Topic.ACCESSLOG)
public class AccessLogSaveEvent extends RemoteEvent {
    private IAccessLog accessLog;

    public IAccessLog getAccessLog(){
        return accessLog;
    }
    @JsonProperty("accessLog")
    public void setAccessLog(AccessLog accessLog){
        this.accessLog = accessLog;
    }

    public void setAccessLog(IAccessLog accessLog){
        this.accessLog = accessLog;
    }

}
