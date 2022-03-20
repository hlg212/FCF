/**
 * 
 */
package com.hlg.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@ConfigurationProperties(prefix = "hlg.log.sql")
@Data
public class SqlLogProperties {
	
	private Boolean enable = true;

}
