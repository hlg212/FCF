package com.hlg.fcf.service;

import com.hlg.fcf.model.ga.ILongRunningRes;

import java.util.List;

public interface LongRunningResService {

    public Integer getUriTimeout(String uri);

    public List<ILongRunningRes> getAllLongRunningRes();
}
