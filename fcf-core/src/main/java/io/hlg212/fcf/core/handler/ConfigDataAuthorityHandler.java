package io.hlg212.fcf.core.handler;

import io.hlg212.fcf.core.properties.DataAuthorityConditionProperties;
import io.hlg212.fcf.core.properties.DataAuthorityConfigSetProperties;
import io.hlg212.fcf.core.properties.DataAuthorityProperties;
import io.hlg212.fcf.model.dam.IDataAuthorityConfigSet;
import io.hlg212.fcf.model.dam.IDataAuthorityPropertyCondition;
import io.hlg212.fcf.service.DataAuthorityService;
import io.hlg212.fcf.util.FworkHelper;
import io.hlg212.fcf.util.SpringHelper;
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
            DataAuthorityService dataAuthorityService = (DataAuthorityService) SpringHelper.getBean(properties.getAuthorityBean());
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
