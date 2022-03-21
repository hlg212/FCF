/**
 * 
 */
package io.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "hlg.log.transaction")
@Data
public class  TransactionLogProperties {
	
	private Boolean enable = true;

}
