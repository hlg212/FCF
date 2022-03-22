package  io.github.hlg212.fcf.service.impl;

import  io.github.hlg212.fcf.model.Model;
import  io.github.hlg212.fcf.model.basic.File;
import org.springframework.transaction.annotation.Transactional;

/**
 * service包装
 * 主要是实现事务的传递
 * 本类定义的方法会被优先调用，再传递到真正的业务service
 *
 * @author huangligui
 * @date 2020年8月18日
 */
public abstract class ServiceWapper<T extends Model> implements CurdieServiceImpl<T> {

	private Object workService;

	public Object getWorkService() {
		return workService;
	}

	public void setWorkService(Object workService) {
		this.workService = workService;
	}

	@Override
	@Transactional
	public T save(T t) {
		if( workService == null || workService == this)
		{
			return CurdieServiceImpl.super.save(t);
		}

		CurdieServiceImpl<T> service = (CurdieServiceImpl)workService;
		return service.save(t);
	}
	
	@Override
	@Transactional
	public void deleteById(Object... id) {
		if( workService == null || workService == this)
		{
			CurdieServiceImpl.super.deleteById(id);
			return;
		}

		CurdieServiceImpl<T> service = (CurdieServiceImpl)workService;
		service.deleteById(id);
	}

	@Override
	@Transactional
	public T update(T t) {

		if( workService == null || workService == this)
		{
			return CurdieServiceImpl.super.update(t);
		}

		CurdieServiceImpl<T> service =  (CurdieServiceImpl)workService;
		return service.update(t);
	}

	@Override
	@Transactional
	public void importSave(File file) {

		if( workService == null || workService == this)
		{
			CurdieServiceImpl.super.importSave(file);
			return;
		}

		ImportServiceImpl<T> service =  (ImportServiceImpl)workService;
		service.importSave(file);
	}

//	@Override
//	public <E extends T> E getById(Object id) {
//		if( workService == null || workService == this)
//		{
//			return CurdieServiceAdapter.super.getById(id);
//		}
//		QueryService<T> service =  (QueryService)workService;
//		return service.getById(id);
//	}

	abstract public Class getServiceClass();
}
