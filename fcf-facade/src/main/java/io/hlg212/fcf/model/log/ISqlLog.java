
package io.hlg212.fcf.model.log;

import io.hlg212.fcf.ISerializable;

import java.util.Date;

public interface ISqlLog extends ISerializable {

    public String getTransactionId();

    public String getSql();

    public Date getCreateTime();
}
