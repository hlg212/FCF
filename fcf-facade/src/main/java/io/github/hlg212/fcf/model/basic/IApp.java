
package  io.github.hlg212.fcf.model.basic;

import  io.github.hlg212.fcf.ISerializable;

public interface IApp extends ISerializable {

	public String getId();

	public String getName();

	public String getCode();

	public String getLoginPage();

	public String getType();

	public String getIcon();

	public String getIndexPage();
}
