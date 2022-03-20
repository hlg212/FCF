package com.hlg.fcf.core.handler;

import com.hlg.fcf.api.dam.DataAuthorityConfigSetApi;
import com.hlg.fcf.api.dam.DataAuthorityPropertyConditionApi;
import com.hlg.fcf.model.dam.IDataAuthorityConfigSet;
import com.hlg.fcf.model.dam.IDataAuthorityPropertyCondition;
import com.hlg.fcf.util.AppContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 数据权限处理接口
 * 基于数据权限管理服务
 *
 * @author huangligui
 * @date 2021年1月11日
 */
@Slf4j
public class DamDataAuthorityHandler extends AbsDataAuthorityHandler {


    @Autowired
    private DataAuthorityConfigSetApi dataAuthorityConfigSetApi;

    @Autowired
    private DataAuthorityPropertyConditionApi dataAuthorityPropertyConditionApi;

    @Override
    protected Object getDynamicValue(IDataAuthorityPropertyCondition propertyCondition) {
        return dataAuthorityPropertyConditionApi.getValue(propertyCondition.getCode()).getValue();
    }

    @Override
    protected <S extends IDataAuthorityConfigSet> List<S> getDataAuthorityConfigSet() {
        return dataAuthorityConfigSetApi.getDataAuthorityConfigSetByCode(AppContextHelper.getAppCode());
    }

    @Override
    protected List<IDataAuthorityPropertyCondition> getDataAuthorityPropertyConditions(IDataAuthorityConfigSet set) {
        return dataAuthorityPropertyConditionApi.getConditions(set.getId());
    }


}
