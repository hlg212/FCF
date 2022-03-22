package  io.github.hlg212.fcf.core.properties;

import  io.github.hlg212.fcf.model.dam.DataAuthorityConfigSet;
import  io.github.hlg212.fcf.model.dam.DataAuthorityPropertyCondition;
import  io.github.hlg212.fcf.model.dam.IDataAuthorityPropertyCondition;
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

