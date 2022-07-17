package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = AutomateProperties.PREFIX)
@Data
public class AutomateProperties {

	public static final String PREFIX = "fcf.automate";

	private Boolean enable = true ;

	private Boolean enableApp = true ;

	private Boolean enableTask = true ;

	private Boolean enableRes = true ;

	private Boolean enableRole = true ;

	private Boolean enablePo = true ;

}
