
package com.hlg.fcf.model.log;

import com.hlg.fcf.ISerializable;

import java.util.Date;

public interface IAccessLog extends ISerializable {

    public String getId();

    public String getUserId();

    public String getUserName();

    public String getClientIp();

    public String getUri();

    public String getRequestUrl();

    public Date getStartTime();

    public Date getEndTime();

    public Long getDuration();

    public String getAppCode();

    public String getTraceId();

    public String getState();

    public String getRequestParam();

    public String getExceptionMess();

    public String getExceptionStack();

    public String getHeaders();
}
