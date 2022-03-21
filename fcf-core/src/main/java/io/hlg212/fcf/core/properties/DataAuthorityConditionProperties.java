package io.hlg212.fcf.core.properties;

import io.hlg212.fcf.api.Constants;
import io.hlg212.fcf.model.dam.DataAuthorityPropertyCondition;
import io.hlg212.fcf.model.dam.DataAuthorityPropertyConditionValue;
import io.hlg212.fcf.model.dam.IDataAuthorityPropertyCondition;
import lombok.Data;

@Data
public class DataAuthorityConditionProperties extends DataAuthorityPropertyCondition {

    public DataAuthorityConditionProperties()
    {
        setType(TYPE_STATIC);
    }

    private String prefix;

    // 参数
    private String param;

    //权限数据值api获取接口，默认采用机构
    private String authorityBean=Constants.ApiContextId.OrgDataAuthorityApi+"FeignClient";

    public void setValue(String value)
    {
        DataAuthorityPropertyConditionValue val = new DataAuthorityPropertyConditionValue();
        this.setValue(val);
    }
}
