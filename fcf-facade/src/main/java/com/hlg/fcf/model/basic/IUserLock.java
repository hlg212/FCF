
package com.hlg.fcf.model.basic;

import com.hlg.fcf.ISerializable;

import java.util.Date;

public interface IUserLock extends ISerializable {

	public String getUsername();

	public String getErrorMsg();

	public Date getLockToTime();

	public Date getCreateTime();

	public String getCreateUser();
}
