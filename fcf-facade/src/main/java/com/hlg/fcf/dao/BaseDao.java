/** 
 * Project Name:frame-facade 
 * File Name:BaseDao.java 
 * Package Name:com.hlg.fcf.dao 
 * Date:2018年8月16日 上午10:08:13 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package com.hlg.fcf.dao;

import com.hlg.fcf.Curd;
import com.hlg.fcf.model.Model;
import com.hlg.fcf.model.Qco;

/** 
 * 
 * 2018年8月16日
 * 
 * @author changwei 
 */
public interface BaseDao<T extends Model> extends Curd<T,Qco> {

    public <M> M getMapper();

    public Class<T> getModelClass();
}
