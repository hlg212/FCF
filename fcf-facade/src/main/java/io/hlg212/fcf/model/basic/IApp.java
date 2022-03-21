
package io.hlg212.fcf.model.basic;

import io.hlg212.fcf.ISerializable;

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
