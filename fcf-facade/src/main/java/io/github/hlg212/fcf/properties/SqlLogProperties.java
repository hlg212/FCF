package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@ConfigurationProperties(prefix = SqlLogProperties.PREFIX)
@Data
public class SqlLogProperties {
	public static final String PREFIX = "fcf.collect.log.sql";

	private Boolean enable = true;

}
