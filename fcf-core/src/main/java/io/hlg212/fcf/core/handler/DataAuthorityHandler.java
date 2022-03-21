package io.hlg212.fcf.core.handler;

import io.hlg212.fcf.dao.BaseDao;
import io.hlg212.fcf.model.QueryCondition;
import io.hlg212.fcf.model.QueryParam;

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
