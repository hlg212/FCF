package io.hlg212.fcf.model.dam;

import lombok.Data;

@Data
public class DataAuthorityPropertyCondition implements IDataAuthorityPropertyCondition {

    private String code;
    private String propertyName;
    private String operation;
    private DataAuthorityPropertyConditionValue value;
    private String type;
    private Boolean isQuery = true;
    private Boolean isDelete = false;
    private Boolean isUpdate = false;
    private Boolean isAdd = false;
}
