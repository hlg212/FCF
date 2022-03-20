package com.hlg.fcf.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 *
 * 数据权限开启相关配置
 * 可以基于本地配置，也可以基于数据权限服务动态配置
 *
 * @author huangligui
 * @date 2021年1月11日
 */

@Data
@ConfigurationProperties(prefix = "hlg.dao.authority")
public class DataAuthorityProperties {

    private boolean configEnable = false;
    private boolean enable = false;
    private boolean damEnable = false;
    private boolean skipException = true;

    private List<DataAuthorityConfigSetProperties> dataAuthorityConfigSets;

}

