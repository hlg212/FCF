
package com.hlg.fcf.model.log;

import com.hlg.fcf.ISerializable;

import java.util.Collection;
import java.util.Date;

public interface ITransactionSqlLog extends ISerializable {

    public String getTransactionId();

    public Date getStartTime();

    public String getUserId();

    public String getUserName();

    public String getAppCode();

    public Collection<ISqlLog> getSqllogs();
}
