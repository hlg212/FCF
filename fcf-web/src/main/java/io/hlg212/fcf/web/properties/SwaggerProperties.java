/**
 * 
 */
package io.hlg212.fcf.web.properties;

import io.hlg212.fcf.web.annotation.SwaggerConditional;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  swagger 配置属性
 * htcf:
 *   swagger:
 *     enable: false
 *
 * @see SwaggerConditional
 * @author huangligui
 * @date 2018年10月19日
 */
@ConfigurationProperties(prefix = "hlg.swagger")
@Data
public class SwaggerProperties {

	private Boolean enable;

}
