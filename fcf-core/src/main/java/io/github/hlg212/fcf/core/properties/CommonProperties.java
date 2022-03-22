package  io.github.hlg212.fcf.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;


@ConfigurationProperties(prefix = "hlg")
public class CommonProperties {

	/**
	 * 用于自定义项目名称对应的端口号
	 */
	private Map<String,Port> application;
	
	/**
	 * 是否启用数据源配置
	 */
	private boolean isEnableDataSource = false;
	
	/**
	 * 数据源配置
	 */
	private Map<String, ExDataSourceProperties> datasource = new HashMap<>();
	
	private Map<String,RedisProperties> redis = new HashMap<>();
	
	class Port {
		
		/**
		 * 项目端口号
		 */
		private Integer port;

		/**
		 *   the port
		 */
		public Integer getPort() {
			return port;
		}

		/**
		 * @param port the port to set
		 */
		public void setPort(Integer port) {
			this.port = port;
		}
		
	}


	/**
	 *   the application
	 */
	public Map<String,Port> getApplication() {
		return application;
	}

	/**
	 * @param application the application to set
	 */
	public void setApplication(Map<String,Port> application) {
		this.application = application;
	}

	/**
	 *   the datasource
	 */
	public Map<String, ExDataSourceProperties> getDatasource() {
		return datasource;
	}

	/**
	 * @param datasource the datasource to set
	 */
	public void setDatasource(Map<String, ExDataSourceProperties> datasource) {
		this.datasource = datasource;
	}

	/**
	 *   the isEnableDataSource
	 */
	public boolean isEnableDataSource() {
		return isEnableDataSource;
	}

	/**
	 * @param isEnableDataSource the isEnableDataSource to set
	 */
	public void setEnableDataSource(boolean isEnableDataSource) {
		this.isEnableDataSource = isEnableDataSource;
	}

	public Map<String,RedisProperties> getRedis() {
		return redis;
	}

	public void setRedis(Map<String,RedisProperties> redis) {
		this.redis = redis;
	}
}
