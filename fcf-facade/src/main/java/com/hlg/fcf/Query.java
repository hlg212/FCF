
package com.hlg.fcf;

import com.hlg.fcf.model.PageInfo;
import com.hlg.fcf.model.Qco;

import java.util.List;

/** 
 * 查询接口基类
 * @author changwei
 * @date 2018年9月6日
 * @param <T> 序列化实体对象
 */
public interface Query<T extends ISerializable, Q extends Qco>{

	/**
	 * 根据主键ID值获取实体对象
	 * 
	 * @param id 主键
	 * @return
	 */
	 <E extends T> E getById(Object id);


	/**
	 * 获取实体单条数据
	 * @param queryProperty
	 * @return
	 */
	<E extends T> E get(Q queryProperty);

	/**
	 * 查询数据数量
	 * @param queryProperty
	 * @return
	 */
	 Integer count(Q queryProperty);
	
	/**
	 *  根据QueryParam对象查询实体信息
	 * @param queryProperty
	 * @return
	 */
	 <E extends T> List<E> find(Q queryProperty);
	
	/**
	 * 根据List<QueryProperty>对象查询对象信息，提供分页查询功能
	 * 
	 * @param queryProperty
	 * @param pageNum 当前页数
	 * @param pageSize 每页显示数量
	 * @return
	 */
	 <E extends T> PageInfo<E> findPage(Q queryProperty, int pageNum, int pageSize);

}
