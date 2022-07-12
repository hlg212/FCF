package  io.github.hlg212.fcf.model.basic;

import  io.github.hlg212.fcf.ISerializable;

public interface IClient  extends ISerializable {

    public String getId();

    public String getAutoApproveScopes();

    public String getScopes();

    public String getSecret();

    public String getAccount();

    public String[] getGrantTypes();

    public String getRegisteredRedirectUri();
}
