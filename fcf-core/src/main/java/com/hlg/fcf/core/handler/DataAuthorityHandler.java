package com.hlg.fcf.core.handler;

import com.hlg.fcf.dao.BaseDao;
import com.hlg.fcf.model.QueryCondition;
import com.hlg.fcf.model.QueryParam;

import java.util.List;


/**
 *
 * 数据权限处理接口
 *
 * @author huangligui
 * @date 2021年1月11日
 */
public interface DataAuthorityHandler {

    public void onAdd(Object o,BaseDao dao);

    public void onUpdate(Object o,BaseDao dao);

    public void onDelete(Object o,BaseDao dao);

    public void onQuery(QueryParam queryParam, BaseDao dao);

}
