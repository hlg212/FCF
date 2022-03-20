package com.hlg.fcf.model.basic;

import com.hlg.fcf.ISerializable;

public interface IBlackWhiteList extends ISerializable {

    public String getId();

    public String getType();

    public String getStart();

    public String getEnd();
}
