/**
 * 
 */
package com.hlg.fcf;

import com.hlg.fcf.annotation.RequestParamOrBody;
import com.hlg.fcf.model.PageInfo;
import com.hlg.fcf.model.PageQuery;
import com.hlg.fcf.model.Qco;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * feign 查询接口基类
 *
 * @author changwei
 * @date 2018年10月8日
 * @param <T> 序列化实体对象
 * @param <Q> 自定义查询属性自定义对象
 */
public interface QueryClientApi<T extends ISerializable, Q extends Qco> extends Query<T,Q>{


	/**
	 * 根据主键ID值获取实体对象
	 *
	 * @param id 主键
	 * @return
	 */
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	@Override
	public <E extends T> E getById(@RequestParam("id") Object id);

	/**
	 * 获取实体单条数据
	 * @param queryProperty
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.POST)
	@Override
	public <E extends T> E get(@RequestParamOrBody Q queryProperty);

	/**
	 * 查询数据数量
	 * @param queryProperty
	 * @return
	 */
	@RequestMapping(value="/count",method=RequestMethod.POST)
	@Override
	public Integer count(@RequestParamOrBody Q queryProperty);
	
	/**
	 *  根据QueryParam对象查询实体信息
	 * @param queryProperty
	 * @return
	 */
	@RequestMapping(value="/find",method=RequestMethod.POST)
	@Override
	public <E extends T> List<E> find(@RequestParamOrBody Q queryProperty);
	
	/**
	 * 根据List<QueryProperty>对象查询对象信息，提供分页查询功能
	 * 
	 * @param queryProperty
	 * @param pageNum 当前页数
	 * @param pageSize 每页显示数量
	 * @return
	 */
	@RequestMapping(value="/findPage",method=RequestMethod.POST)
	@Override
	public <E extends T> PageInfo<E>  findPage(@RequestParamOrBody Q queryProperty, @RequestParam(name="pageNum",defaultValue = Constants.QueryClientApi.PAGE_NUM) int pageNum, @RequestParam(name="pageSize",defaultValue = Constants.QueryClientApi.PAGE_SIZE) int pageSize);

	/**
	 * 根据 PageQuery 对象查询对象信息，提供分页查询功能
	 * 防止findPage前端json提交时让接口访问比较丑陋的问题
	 *
	 * @param pageQuery
	 * @return
	 */
	@RequestMapping(value="/pageQuery",method=RequestMethod.POST)
	public <E extends T> PageInfo<E>  pageQuery(@RequestBody PageQuery<Q> pageQuery);

}
