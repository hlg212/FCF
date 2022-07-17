package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = EsbProperties.PREFIX)
@Data
public class EsbProperties {
	public static final String PREFIX = "fcf.esb";

	private Boolean enable = true;

	private Boolean local = false;

}
