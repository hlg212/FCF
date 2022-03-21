
package io.hlg212.fcf;

import io.hlg212.fcf.annotation.RequestParamOrBody;
import io.hlg212.fcf.model.Qco;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


public interface CurdClientApi<T extends ISerializable, Q extends Qco> extends Curd<T, Q>,QueryClientApi<T,Q> {

	@RequestMapping(value="/save", method = {RequestMethod.POST})
	public T save(@RequestParamOrBody T t);
	
	@RequestMapping(value="/update", method = {RequestMethod.POST})
	public void updateNotReturn(@RequestParamOrBody T t);

	@RequestMapping(value="/updateAndReturn", method = {RequestMethod.POST})
	@Override
	public T update(@RequestParamOrBody T t);
	
	@RequestMapping(value="/deleteById", method = {RequestMethod.GET})
	public void deleteById(@RequestParam("ids") Object... ids);
}
