/**
 * 
 */
package com.hlg.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "hlg.cache")
@Data
public class CacheProperties {
	
	private Boolean enable = true;

}
