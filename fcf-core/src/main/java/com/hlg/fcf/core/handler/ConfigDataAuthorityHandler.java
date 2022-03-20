package com.hlg.fcf.core.handler;

import com.hlg.fcf.core.properties.DataAuthorityConditionProperties;
import com.hlg.fcf.core.properties.DataAuthorityConfigSetProperties;
import com.hlg.fcf.core.properties.DataAuthorityProperties;
import com.hlg.fcf.model.dam.IDataAuthorityConfigSet;
import com.hlg.fcf.model.dam.IDataAuthorityPropertyCondition;
import com.hlg.fcf.service.DataAuthorityService;
import com.hlg.fcf.util.FworkHelper;
import com.hlg.fcf.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 数据权限处理接口
 * 基于配置的
 *
 * @author huangligui
 * @date 2021年1月11日
 */
@Slf4j
public class ConfigDataAuthorityHandler extends AbsDataAuthorityHandler implements  InitializingBean {


    @Override
    protected Object getDynamicValue(IDataAuthorityPropertyCondition propertyCondition) {
        if( propertyCondition instanceof DataAuthorityConditionProperties )
        {
            DataAuthorityConditionProperties properties = (DataAuthorityConditionProperties) propertyCondition;
            DataAuthorityService dataAuthorityService = (DataAuthorityService) SpringUtils.getBean(properties.getAuthorityBean());
            return dataAuthorityService.getAuthoritys(FworkHelper.getUid(), properties.getParam());
        }
        return null;
    }


    @Override
    protected List<DataAuthorityConfigSetProperties> getDataAuthorityConfigSet() {
        return dataAuthorityProperties.getDataAuthorityConfigSets();
    }

    @Override
    protected List<DataAuthorityConditionProperties> getDataAuthorityPropertyConditions(IDataAuthorityConfigSet set) {
        List result = new ArrayList();

       if( set instanceof DataAuthorityConfigSetProperties)
       {
           return ((DataAuthorityConfigSetProperties) set).getConditions();

       }
       return null;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        refresh();
    }
}
