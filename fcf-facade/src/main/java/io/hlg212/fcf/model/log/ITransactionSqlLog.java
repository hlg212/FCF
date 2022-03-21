
package io.hlg212.fcf.model.log;

import io.hlg212.fcf.ISerializable;

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
