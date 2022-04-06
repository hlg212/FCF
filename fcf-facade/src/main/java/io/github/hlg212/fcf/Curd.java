package  io.github.hlg212.fcf;

import  io.github.hlg212.fcf.model.Qco;

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
	 *
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
