
package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;



@ConfigurationProperties(prefix = PackageProperties.PREFIX)
@Data
@Component
public class PackageProperties {
	public static final String PREFIX = "fcf.package";

	private String company ;

	private String app ;

	private String basePackage;

	private String dao ;

	private String[] event;

}
