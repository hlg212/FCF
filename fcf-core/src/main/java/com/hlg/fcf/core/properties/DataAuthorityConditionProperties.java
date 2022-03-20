package com.hlg.fcf.core.properties;

import com.hlg.fcf.api.Constants;
import com.hlg.fcf.model.dam.DataAuthorityPropertyCondition;
import com.hlg.fcf.model.dam.DataAuthorityPropertyConditionValue;
import com.hlg.fcf.model.dam.IDataAuthorityPropertyCondition;
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
