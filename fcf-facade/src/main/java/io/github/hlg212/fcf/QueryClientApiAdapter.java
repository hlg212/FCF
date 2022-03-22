/**
 * 
 */
package  io.github.hlg212.fcf;

import  io.github.hlg212.fcf.annotation.RequestParamOrBody;
import  io.github.hlg212.fcf.model.PageInfo;
import  io.github.hlg212.fcf.model.PageQuery;
import  io.github.hlg212.fcf.model.Qco;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * feign 查询接口基类
 *
 * @author huangligui
 * @date 2018年10月8日
 * @param <T> 序列化实体对象
 * @param <Q> 自定义查询属性自定义对象
 */
public interface QueryClientApiAdapter<T extends ISerializable, Q extends Qco> extends QueryClientApi<T,Q>{

	@Override
	default  public <E extends T> E getById(@RequestParam("id") Object id){return null;};

	@Override
	default public <E extends T> E get(@RequestParamOrBody Q queryProperty){return null;}

	@Override
	default public Integer count(@RequestParamOrBody Q queryProperty){return null;}

	@Override
	default public <E extends T> List<E> find(@RequestParamOrBody Q queryProperty){return null;}


	@Override
	default public <E extends T> PageInfo<E>  findPage(@RequestParamOrBody Q queryProperty, @RequestParam(name = "pageNum", defaultValue = Constants.QueryClientApi.PAGE_NUM) int pageNum, @RequestParam(name = "pageSize", defaultValue = Constants.QueryClientApi.PAGE_SIZE) int pageSize)
	{return null;}

	@Override
	default public <E extends T> PageInfo<E>  pageQuery(@RequestBody PageQuery<Q> pageQuery)
	{return null;}

}
