/** 
 * Project Name:frame-facade 
 * File Name:BaseDao.java 
 * Package Name: io.github.hlg212.fcf.dao
 * Date:2018年8月16日 上午10:08:13 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.dao;

import  io.github.hlg212.fcf.Curd;
import  io.github.hlg212.fcf.model.Model;
import  io.github.hlg212.fcf.model.Qco;

/** 
 * 
 * 2018年8月16日
 * 
 * @author huangligui 
 */
public interface BaseDao<T extends Model> extends Curd<T,Qco> {

    public <M> M getMapper();

    public Class<T> getModelClass();
}
