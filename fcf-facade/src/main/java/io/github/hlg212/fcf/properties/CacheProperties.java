/**
 * 
 */
package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "fcf.cache")
@Data
public class CacheProperties {
	
	private Boolean enable = true;

}
