package io.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("hlg.api.client")
@Data
public class ApiClientProperties {

	// 开启耗时调用
	private Boolean longRunningResEnable=false;

}
