
package  io.github.hlg212.fcf;

import  io.github.hlg212.fcf.model.PageInfo;
import  io.github.hlg212.fcf.model.Qco;

import java.util.List;

/** 
 * 查询接口基类
 * @author huangligui
 * @date 2018年9月6日
 * @param <T> 序列化实体对象
 */
public interface Query<T extends ISerializable, Q extends Qco>{

	/**
	 * 根据主键ID值获取实体对象
	 * 
	 * @param id 主键
	 *
	 */
	 <E extends T> E getById(Object id);


	/**
	 * 获取实体单条数据
	 * @param queryProperty
	 *
	 */
	<E extends T> E get(Q queryProperty);

	/**
	 * 查询数据数量
	 * @param queryProperty
	 *
	 */
	 Integer count(Q queryProperty);
	
	/**
	 *  根据QueryParam对象查询实体信息
	 * @param queryProperty
	 *
	 */
	 <E extends T> List<E> find(Q queryProperty);
	
	/**
	 * 根据List<QueryProperty>对象查询对象信息，提供分页查询功能
	 * 
	 * @param queryProperty
	 * @param pageNum 当前页数
	 * @param pageSize 每页显示数量
	 *
	 */
	 <E extends T> PageInfo<E> findPage(Q queryProperty, int pageNum, int pageSize);

}