/**
 * 
 */
package  io.github.hlg212.fcf.web.properties;

import  io.github.hlg212.fcf.web.annotation.SwaggerConditional;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  swagger 配置属性
 * fcf:
 *   swagger:
 *     enable: false
 *
 * @see SwaggerConditional
 * @author huangligui
 * @date 2018年10月19日
 */
@ConfigurationProperties(prefix = SwaggerProperties.PREFIX)
@Data
public class SwaggerProperties {

	public static final String PREFIX = "fcf.swagger";

	private Boolean enable;

}
