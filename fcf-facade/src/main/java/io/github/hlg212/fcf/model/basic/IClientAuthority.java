package io.github.hlg212.fcf.model.basic;

import io.github.hlg212.fcf.ISerializable;

import java.util.List;

public interface IClientAuthority extends ISerializable {

    public String getRoleId();

    public List<String> getAuthoritys();
}
