
package com.hlg.fcf.model.basic;

import com.hlg.fcf.ISerializable;

public interface IApp extends ISerializable {

	public String getId();

	public String getName();

	public String getCode();

	public String getContext();

	public String getLoginPage();

	public String getType();

	public String getIcon();

	public String getIndexPage();
}
