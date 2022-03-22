
package  io.github.hlg212.fcf;

import  io.github.hlg212.fcf.annotation.RequestParamOrBody;
import  io.github.hlg212.fcf.model.Qco;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * feign 增删改查接口基类
 * @author huangligui
 * @date 2018年10月8日
 * @param <T> 序列化实体对象
 * @param <Q> 自定义查询属性自定义对象
 */
public interface CurdClientApiAdapter<T extends ISerializable, Q extends Qco> extends CurdClientApi<T,Q>,QueryClientApiAdapter<T,Q> {

	@Override
	default  public T save(@RequestParamOrBody T t){ return null;}

	@Override
	default public void updateNotReturn(@RequestParamOrBody T t){ }

	@Override
	default public T update(@RequestParamOrBody T t){ return null;}

	@Override
	default public void deleteById(@RequestParam("ids") Object... ids){ };
}
