package io.hlg212.fcf.dao;

import io.hlg212.fcf.model.Model;
import io.hlg212.fcf.model.QueryParam;

/**
 *  baseDao查询条件过滤接口， getById、get方法不受条件控制
 *  
 * @author huangligui
 *
 * @param <T>
 */
public interface QueryAuthInterface<T extends Model> {

	void addAuthQueryParam(QueryParam queryParam);
}
