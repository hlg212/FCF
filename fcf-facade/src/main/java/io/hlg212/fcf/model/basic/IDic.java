package io.hlg212.fcf.model.basic;

import io.hlg212.fcf.ISerializable;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2018-11-06 09:34
 **/
public interface IDic extends ISerializable,ITree {
    /**
     * 获取字典bh
     * @return
     */
    public String getKey();

    /**
     * 获取字典val
     * @return
     */
    public String getValue();

    /**
     * 获取字典mc
     * @return
     */
    public String getMc();

    public String getAppId();

}
