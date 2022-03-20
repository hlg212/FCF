package com.hlg.fcf.model.basic;

import com.hlg.fcf.ISerializable;

public interface IClient  extends ISerializable {

    public String getId();

    public String getAutoApproveScopes();

    public String getScopes();

    public String getSecret();

    public String getName();

    public String getRegisteredRedirectUri();
}
