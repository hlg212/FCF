/**
 * 
 */
package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "hlg.esb")
@Data
public class EsbProperties {
	
	private Boolean enable = true;

	private Boolean local = false;

}
