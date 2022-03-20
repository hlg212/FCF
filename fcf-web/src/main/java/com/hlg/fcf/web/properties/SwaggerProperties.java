/**
 * 
 */
package com.hlg.fcf.web.properties;

import com.hlg.fcf.web.annotation.SwaggerConditional;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  swagger 配置属性
 * htcf:
 *   swagger:
 *     enable: false
 *
 * @see SwaggerConditional
 * @author changwei
 * @date 2018年10月19日
 */
@ConfigurationProperties(prefix = "hlg.swagger")
@Data
public class SwaggerProperties {

	private Boolean enable;

}
