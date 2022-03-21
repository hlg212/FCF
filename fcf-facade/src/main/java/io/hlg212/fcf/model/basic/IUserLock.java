
package io.hlg212.fcf.model.basic;

import io.hlg212.fcf.ISerializable;

import java.util.Date;

public interface IUserLock extends ISerializable {

	public String getUsername();

	public String getErrorMsg();

	public Date getLockToTime();

	public Date getCreateTime();

	public String getCreateUser();
}
