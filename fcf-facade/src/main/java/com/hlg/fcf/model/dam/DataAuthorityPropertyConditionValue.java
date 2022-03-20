package com.hlg.fcf.model.dam;

import lombok.Data;

@Data
public class DataAuthorityPropertyConditionValue implements IDataAuthorityPropertyConditionValue {

    private Object value;

    @Override
    public Object getValue(){
        return value;
    }

}
