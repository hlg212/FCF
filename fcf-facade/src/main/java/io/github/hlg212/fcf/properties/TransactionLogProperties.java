
package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = TransactionLogProperties.PREFIX)
@Data
public class  TransactionLogProperties {
	public static final String PREFIX = "fcf.collect.log.transaction";

	private Boolean enable = true;

}
