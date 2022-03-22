/**
 * 
 */
package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;


@ConfigurationProperties("hlg")
@Data
public class FeignProperties {

	@NestedConfigurationProperty
	private Map<String,FeignAppProperties> feign = new HashMap<>();

	@Data
	public class FeignAppProperties {

		private String name ;

		private String gateway ;

		private String url ;
	}

}
