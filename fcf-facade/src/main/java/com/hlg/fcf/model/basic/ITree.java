package com.hlg.fcf.model.basic;

import java.util.List;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2018-11-19 14:00
 **/
public interface ITree {

    public String getId();
    public String getPid();
    public <E> List<E> getChildren();

}
