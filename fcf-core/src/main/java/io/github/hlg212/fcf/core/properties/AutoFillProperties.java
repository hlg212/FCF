package  io.github.hlg212.fcf.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashSet;
import java.util.Set;


@ConfigurationProperties(prefix = AutoFillProperties.PREFIX)
public class AutoFillProperties {
	public static final String PREFIX = "fcf.dao.fill";

	private Set<String> insert = new LinkedHashSet<>(10);
	private Set<String> update = new LinkedHashSet<>(10);
	private Set<String> insertUpdate = new LinkedHashSet<>(10);

	public Set<String> getInsert() {
		return insert;
	}

	public void setInsert(Set<String> insert) {
		this.insert.addAll(insert);
	}

	public Set<String> getUpdate() {
		return update;
	}

	public void setUpdate(Set<String> update) {
		this.update.addAll(update);
	}

	public Set<String> getInsertUpdate() {
		return insertUpdate;
	}

	public void setInsertUpdate(Set<String> insertUpdate) {
		this.insertUpdate.addAll(insertUpdate);
	}
}
