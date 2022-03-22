package  io.github.hlg212.fcf.model.basic;

import  io.github.hlg212.fcf.ISerializable;

public interface IRes extends ISerializable, ITree {

    public String getName();

    public String getType();

    public String getCode();

    public String getPath();

    public String getIcon();

    public String getId();

}
