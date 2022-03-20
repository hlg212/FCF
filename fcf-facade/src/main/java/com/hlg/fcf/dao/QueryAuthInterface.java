package com.hlg.fcf.dao;

import com.hlg.fcf.model.Model;
import com.hlg.fcf.model.QueryParam;

/**
 *  baseDao查询条件过滤接口， getById、get方法不受条件控制
 *  
 * @author changwei
 *
 * @param <T>
 */
public interface QueryAuthInterface<T extends Model> {

	void addAuthQueryParam(QueryParam queryParam);
}
