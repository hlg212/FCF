package  io.github.hlg212.fcf.model.basic;

import  io.github.hlg212.fcf.ISerializable;

public interface IUser extends ISerializable {

    public String getName();

    public String getPassword();

    public String getAccount();

    public String getId();

    public String getOrgId();

    public  IOrg getOrg();
}
