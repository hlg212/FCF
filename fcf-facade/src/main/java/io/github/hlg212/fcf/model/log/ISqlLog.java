
package  io.github.hlg212.fcf.model.log;

import  io.github.hlg212.fcf.ISerializable;

import java.util.Date;

public interface ISqlLog extends ISerializable {

    public String getTransactionId();

    public String getSql();

    public Date getCreateTime();
}
