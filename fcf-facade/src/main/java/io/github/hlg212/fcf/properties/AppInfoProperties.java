/**
 * 
 */
package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  swagger 配置属性
 * htcf:
 *   swagger:
 *     enable: 1
 *
 * @author huangligui
 * @date 2018年10月19日
 */
@ConfigurationProperties(prefix = "info.app")
@Data
public class AppInfoProperties {

	private String name;
	private String description;
	private String version;

}
