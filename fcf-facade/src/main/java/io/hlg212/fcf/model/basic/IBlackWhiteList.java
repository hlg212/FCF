package io.hlg212.fcf.model.basic;

import io.hlg212.fcf.ISerializable;

public interface IBlackWhiteList extends ISerializable {

    public String getId();

    public String getType();

    public String getStart();

    public String getEnd();
}
