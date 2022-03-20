package com.hlg.fcf.core.properties;

import com.hlg.fcf.model.dam.DataAuthorityConfigSet;
import com.hlg.fcf.model.dam.DataAuthorityPropertyCondition;
import com.hlg.fcf.model.dam.IDataAuthorityPropertyCondition;
import lombok.Data;

import java.util.List;
/**
 * 基于配置的数据权限
 *
 * @author huangligui
 * @date 2021年1月11日
 */
@Data
public class DataAuthorityConfigSetProperties extends DataAuthorityConfigSet {

    private List<DataAuthorityConditionProperties> conditions;

}

