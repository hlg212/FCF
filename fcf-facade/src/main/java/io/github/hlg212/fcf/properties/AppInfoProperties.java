package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = AppInfoProperties.PREFIX)
@Data
public class AppInfoProperties {
	public static final String PREFIX = "info.app";

	private String name;
	private String description;
	private String version;

}
