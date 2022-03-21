/** 
 * Project Name:frame-facade 
 * File Name:CurdService.java 
 * Package Name:io.hlg212.fcf.service
 * Date:2018年7月30日 上午11:15:02 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package io.hlg212.fcf;

import io.hlg212.fcf.model.Qco;

/** 
 * curd接口基类
 *
 * @author huangligui
 * @date 2018年9月25日
 * @param <T> 序列化实体对象
 */
public interface Curd<T extends ISerializable,Q extends Qco> extends Query<T,Q>{

	/**
	 * 
	 * 实体数据保存
	 * 
	 * @param t
	 * @return
	 */
	 T save(T t);
	
	/**
	 * 
	 * 根据主键删除数据
	 * 
	 * @param ids
	 */
	 void deleteById(Object... ids);
	
	/**
	 * 
	 * 根据主键修改数据
	 * 
	 * @param t
	 */
	 T update(T t);
	
	
}
