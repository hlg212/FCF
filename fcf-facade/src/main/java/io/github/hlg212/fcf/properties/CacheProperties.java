
package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = CacheProperties.PREFIX)
@Data
public class CacheProperties {
	public static final String PREFIX = "fcf.cache";

	
	private Boolean enable = true;

}
