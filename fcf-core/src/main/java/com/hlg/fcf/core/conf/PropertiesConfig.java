/**
 * 
 */
package com.hlg.fcf.core.conf;

import com.hlg.fcf.core.properties.CommonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PropertiesConfig {

	@Bean
	public CommonProperties commonProperties() {
		return new CommonProperties();
	}
}
