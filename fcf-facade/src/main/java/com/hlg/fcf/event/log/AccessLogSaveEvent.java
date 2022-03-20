package com.hlg.fcf.event.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hlg.fcf.annotation.RemoteEventAnnotation;
import com.hlg.fcf.event.Constants;
import com.hlg.fcf.event.RemoteEvent;
import com.hlg.fcf.model.log.AccessLog;
import com.hlg.fcf.model.log.IAccessLog;
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
