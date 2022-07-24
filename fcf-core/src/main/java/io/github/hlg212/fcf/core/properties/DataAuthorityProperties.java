package io.github.hlg212.fcf.core.properties;

import io.github.hlg212.fcf.properties.PrefixConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 *
 * 数据权限开启相关配置
 * 可以基于本地配置，也可以基于数据权限服务动态配置
 *
 * @author huangligui
 * @date 2021年1月11日
 */

@Data
@ConfigurationProperties(prefix = DataAuthorityProperties.PREFIX)
public class DataAuthorityProperties {

    public static final String PREFIX = PrefixConstants.DATA_AUTHORITY_PROPERTIES_PREFIX;

    private boolean configEnable = false;
    private boolean enable = false;
    private boolean damEnable = false;
    private boolean skipException = true;

    private List<DataAuthorityConfigSetProperties> dataAuthorityConfigSets;

}

