package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = ApiClientProperties.PREFIX)
@Data
public class ApiClientProperties {
	public static final String PREFIX = "fcf.api.client";

	// 开启耗时调用
	private Boolean longRunningResEnable=false;

}
