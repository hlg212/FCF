
package  io.github.hlg212.fcf.model.mc;

import  io.github.hlg212.fcf.ISerializable;

import java.util.Date;

public interface IOnlineUser extends ISerializable {

	public Date getLoginDate();

	public String getId();

	public String getUserId();

	public String getClientId();

	public String getToken();

	public String getUsername();

	public String getAppCode();

	public String getIp();
}
