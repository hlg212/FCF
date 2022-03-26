/**
 * 
 */
package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "fcf.log.transaction")
@Data
public class  TransactionLogProperties {
	
	private Boolean enable = true;

}
