package  io.github.hlg212.fcf.service;

import  io.github.hlg212.fcf.model.basic.IDict;

import java.util.List;
import java.util.Map;

public interface DictService{
    /**
     * 获取字典值
     * @param code
     * @param key
     *  
     */
    public String getVal(String code, String key);

    /**
     * 获取字典名称
     * @param code
     * @param key
     *  
     */
    public String getName(String code, String key);

    /**
     * 获取字典Map<key, val>
     * @param code
     * @param key
     *  
     */
    public Map<String, String> getMapVal(String code, String key);

    /**
     * 获取字典Map<key, mc>
     * @param code
     * @param key
     *  
     */
    public Map<String, String> getMapName(String code, String key);

    /**
     * 获取指定系统所有的字典
     *  
     */
    public List<IDict> getAllDicts(String code);

    public void refresh(String appCode);
}
