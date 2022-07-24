package io.github.hlg212.fcf.core.conf;

import io.github.hlg212.fcf.annotation.DataAuthorityConditional;
import io.github.hlg212.fcf.core.handler.CompositeDataAuthorityHandler;
import io.github.hlg212.fcf.core.handler.ConfigDataAuthorityHandler;
import io.github.hlg212.fcf.core.handler.DamDataAuthorityHandler;
import io.github.hlg212.fcf.core.properties.DataAuthorityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@EnableConfigurationProperties(DataAuthorityProperties.class)
@DataAuthorityConditional
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DataAuthorityConfig {

    @Bean
    @ConditionalOnProperty(prefix = DataAuthorityProperties.PREFIX, name = "configEnable", havingValue = "true" )
    public ConfigDataAuthorityHandler configDataAuthorityHandler()
    {
        return new ConfigDataAuthorityHandler();
    }


    @Bean
    @Primary
    public CompositeDataAuthorityHandler compositeDataAuthorityHandler()
    {
        return new CompositeDataAuthorityHandler();
    }


    @Bean
    @ConditionalOnProperty(prefix = DataAuthorityProperties.PREFIX, name = "damEnable", havingValue = "true" )
    public DamDataAuthorityHandler damDataAuthorityHandler()
    {
        return new DamDataAuthorityHandler();
    }

}
