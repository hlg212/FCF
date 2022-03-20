package com.hlg.fcf.model.dam;

import lombok.Data;

import java.util.Map;

@Data
public class DataAuthorityConfigSetInfo extends DataAuthorityConfigSet {
    
    private Map<String,String> filedInfo;
}
