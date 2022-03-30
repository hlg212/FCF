package  io.github.hlg212.fcf.model.basic;

import  io.github.hlg212.fcf.ISerializable;

/**
 * @author  huangligui
 * @create: 2018-11-06 09:34
 **/
public interface IDict extends ISerializable,ITree {
    /**
     * 获取字典bh
     *  
     */
    public String getKey();

    /**
     * 获取字典val
     *  
     */
    public String getValue();

    /**
     * 获取字典mc
     *  
     */
    public String getName();

    public String getAppId();

}
