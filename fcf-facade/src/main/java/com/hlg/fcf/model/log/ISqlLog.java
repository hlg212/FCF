
package com.hlg.fcf.model.log;

import com.hlg.fcf.ISerializable;

import java.util.Date;

public interface ISqlLog extends ISerializable {

    public String getTransactionId();

    public String getSql();

    public Date getCreateTime();
}
