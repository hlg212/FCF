/** 
 * Project Name:frame-web 
 * File Name:CurdController.java 
 * Package Name: io.github.hlg212.fcf.controller
 * Date:2018年8月16日 下午12:56:42 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.web.controller;

import  io.github.hlg212.fcf.CurdClientApi;
import  io.github.hlg212.fcf.ISerializable;
import  io.github.hlg212.fcf.annotation.RequestParamOrBody;
import  io.github.hlg212.fcf.model.Qco;
import  io.github.hlg212.fcf.service.CurdService;
import  io.github.hlg212.fcf.util.ExceptionHelper;
import  io.github.hlg212.fcf.util.LogHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Arrays;


/**
 * 增删改查控制器
 *
 * 如果只需要查询接口，请使用 QueryController
 *
 * @author huangligui
 * @date 2020年8月18日
 */
public interface CurdController<T extends ISerializable, Q extends Qco> extends CurdClientApi<T,Q>,QueryController<T, Q>{


//	@Override
//	default  <S extends QueryService<T> & CurdService<T>>S  getService()
//	{
//		Class bc = ResolvableType.forClass(getClass()).getInterfaces()[0].getGeneric(0).resolve();
//		ResolvableType type = ResolvableType.forClassWithGenerics(CurdService.class,bc);
//		String beans[] = SpringHelper.getApplicationContext().getBeanNamesForType(type);
//		return (S) SpringHelper.getBean(beans[0]);
//	}

	default public <S extends CurdService<T> >S  getCurdService()
	{
		//return (S)ServiceDaoHelper.getService(getClass(),CurdService.class);
		return (S)getService(CurdService.class);
	}

	@ResponseBody
	@ApiOperation("保存实体数据")
	@Override
	default public T save(@RequestParamOrBody  @Valid T t) {
		LogHelper.getLog(getClass()).debug("保存实体数据.{}", t);

		return getCurdService().save(t);
	}
	
	@ResponseBody
	@ApiOperation("根据主键值，修改实体属性值数据")
	default public void updateNotReturn(@RequestParamOrBody T t) {
		LogHelper.getLog(getClass()).debug("根据主键值，修改实体属性值数据.{}", t);
		getCurdService().update(t);
	}

	@ResponseBody
	@Override
	@ApiOperation("根据主键值，修改实体属性值数据,返回修改后最新的数据!")
	default  public  T update(@RequestParamOrBody T t)
	{
		LogHelper.getLog(getClass()).debug("根据主键值，修改实体属性值数据.{}", t);

		return getCurdService().update(t);
	}

	@ResponseBody
	@ApiOperation("根据主键删除数据")
	@Override
	@ApiImplicitParam(name="ids", value="主键ID", required=true, allowMultiple=true)
	@RequestMapping(value="/deleteById", method = {RequestMethod.GET,RequestMethod.POST})
	default public void deleteById(@RequestParam(required=false,name = "ids") Object... ids) {
		if(ids == null || ids.length == 0) {
			ExceptionHelper.throwBusinessException("未传入待删除的主键值,请检查其ids传参.");
		}
		Object[] param = new Object[ids.length];
		System.arraycopy(ids, 0, param, 0 , param.length);
		LogHelper.getLog(getClass()).debug("根据主键删除数据.{}", Arrays.toString(param));
		getCurdService().deleteById(param);
	}

}