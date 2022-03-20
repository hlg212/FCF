package com.hlg.fcf.model.basic;

import com.hlg.fcf.ISerializable;

public interface IUser extends ISerializable {

    public String getName();

    public String getPassword();

    public String getAccount();

    public String getId();

    public String getOrgId();

    public  IOrg getOrg();
}
