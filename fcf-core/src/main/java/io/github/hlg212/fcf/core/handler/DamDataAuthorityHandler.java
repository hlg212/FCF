package  io.github.hlg212.fcf.core.handler;

import  io.github.hlg212.fcf.api.dam.DataAuthorityConfigSetApi;
import  io.github.hlg212.fcf.api.dam.DataAuthorityPropertyConditionApi;
import  io.github.hlg212.fcf.model.dam.IDataAuthorityConfigSet;
import  io.github.hlg212.fcf.model.dam.IDataAuthorityPropertyCondition;
import  io.github.hlg212.fcf.util.AppContextHelper;
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
