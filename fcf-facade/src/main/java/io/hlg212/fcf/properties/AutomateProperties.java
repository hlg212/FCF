/**
 * 
 */
package io.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "hlg.automate")
@Data
public class AutomateProperties {
	
	private Boolean enable = true ;

	private Boolean enableApp = true ;

	private Boolean enableTask = true ;

	private Boolean enableRes = true ;

	private Boolean enableRole = true ;

	private Boolean enableSjrzBms = true ;

}
