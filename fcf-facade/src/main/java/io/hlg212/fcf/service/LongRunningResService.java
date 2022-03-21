package io.hlg212.fcf.service;

import io.hlg212.fcf.model.ga.ILongRunningRes;

import java.util.List;

public interface LongRunningResService {

    public Integer getUriTimeout(String uri);

    public List<ILongRunningRes> getAllLongRunningRes();
}
