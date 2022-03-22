/**
 * 
 */
package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@ConfigurationProperties(prefix = "hlg.log.access")
@Data
public class AccessLogProperties {
	
	private Boolean enable = true;

	// 忽略拦截的表达式
	private List<String> ignores;
	// 只记录异常日志
	private Boolean onlySaveException = false;
	// 只记录入口日志
	private Boolean onlySaveFirst = true;

	private List<String> headers;
}
