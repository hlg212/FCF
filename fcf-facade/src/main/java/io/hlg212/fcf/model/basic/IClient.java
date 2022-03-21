package io.hlg212.fcf.model.basic;

import io.hlg212.fcf.ISerializable;

public interface IClient  extends ISerializable {

    public String getId();

    public String getAutoApproveScopes();

    public String getScopes();

    public String getSecret();

    public String getName();

    public String getRegisteredRedirectUri();
}
