package io.hlg212.fcf.model.dam;

import lombok.Data;

@Data
public class DataAuthorityConfigSet implements  IDataAuthorityConfigSet {

    private String id;
    private String code;
    private Boolean isQuery;
    private Boolean isDelete;
    private Boolean isUpdate;
    private Boolean isAdd;

}
