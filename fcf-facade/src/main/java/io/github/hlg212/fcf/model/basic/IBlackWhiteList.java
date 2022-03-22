package  io.github.hlg212.fcf.model.basic;

import  io.github.hlg212.fcf.ISerializable;

public interface IBlackWhiteList extends ISerializable {

    public String getId();

    public String getType();

    public String getStart();

    public String getEnd();
}
