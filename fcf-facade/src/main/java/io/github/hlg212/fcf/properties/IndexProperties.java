/**
 * 
 */
package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = IndexProperties.PREFIX)
@Data
public class IndexProperties {
	public static final String PREFIX = "fcf.index";

	private Boolean enable = true;

	private String indexPath = "redirect:/views/index.html";

}
