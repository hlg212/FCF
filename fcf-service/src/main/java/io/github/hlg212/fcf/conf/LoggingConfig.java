package io.github.hlg212.fcf.conf;

import io.github.hlg212.fcf.log.AccessIdLoggingParam;
import io.github.hlg212.fcf.log.SwTraceIdLoggingParam;
import io.github.hlg212.fcf.properties.LogBackProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LogBackProperties.class)
public class LoggingConfig {

    @Bean
    public SwTraceIdLoggingParam swTraceIdLoggingParam()
    {
        return new SwTraceIdLoggingParam();
    }
    @Bean
    public AccessIdLoggingParam accessIdLoggingParam()
    {
        return new AccessIdLoggingParam();
    }


}
