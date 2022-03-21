/**
 * 
 */
package io.hlg212.fcf.core.conf;

import io.hlg212.fcf.core.properties.CommonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PropertiesConfig {

	@Bean
	public CommonProperties commonProperties() {
		return new CommonProperties();
	}
}
