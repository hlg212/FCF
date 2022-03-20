/**
 * 
 */
package com.hlg.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "hlg.index")
@Data
public class IndexProperties {
	
	private Boolean enable = true;

	private String indexPath = "redirect:/views/index.html";

}
