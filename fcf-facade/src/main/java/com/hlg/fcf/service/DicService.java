package com.hlg.fcf.service;

import com.hlg.fcf.model.basic.IDic;

import java.util.List;
import java.util.Map;

public interface DicService{
    /**
     * 获取字典值
     * @param code
     * @param key
     * @return
     */
    public String getVal(String code, String key);

    /**
     * 获取字典名称
     * @param code
     * @param key
     * @return
     */
    public String getName(String code, String key);

    /**
     * 获取字典Map<key, val>
     * @param code
     * @param key
     * @return
     */
    public Map<String, String> getMapVal(String code, String key);

    /**
     * 获取字典Map<key, mc>
     * @param code
     * @param key
     * @return
     */
    public Map<String, String> getMapName(String code, String key);

    /**
     * 获取指定系统所有的字典
     * @return
     */
    public List<IDic> getAllDic(String code);

    public void refresh(String appCode);
}
