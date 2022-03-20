package com.hlg.fcf.model.basic;

import com.hlg.fcf.ISerializable;

public interface IRes extends ISerializable, ITree {

    public String getName();

    public String getType();

    public String getCode();

    public String getPath();

    public String getIcon();

    public String getId();

}
