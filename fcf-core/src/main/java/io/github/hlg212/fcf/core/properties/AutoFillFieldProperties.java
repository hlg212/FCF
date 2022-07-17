
package  io.github.hlg212.fcf.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashSet;
import java.util.Set;


@ConfigurationProperties(prefix = AutoFillFieldProperties.PREFIX)
@Data
public class AutoFillFieldProperties {
	public static final String PREFIX = "fcf.dao.fill.field";

	private Set<String> updateTime = new LinkedHashSet<>(10);
	private Set<String> createTime = new LinkedHashSet<>(10);

	private Set<String> createUser = new LinkedHashSet<>(10);
	private Set<String> updateUser = new LinkedHashSet<>(10);
	private Set<String> createUserId = new LinkedHashSet<>(10);
	private Set<String> updateUserId = new LinkedHashSet<>(10);

}
