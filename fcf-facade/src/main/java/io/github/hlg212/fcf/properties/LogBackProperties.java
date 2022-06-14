package io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "fcf.log.logback")
@Data
@Component
public class LogBackProperties {
	
	private Boolean enableSwgrpcLog = true;

    private Boolean enable = true;

	private String swrpcPattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{tid}] [%thread] %-5level %logger{36} -%msg%n";

}
