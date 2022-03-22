package  io.github.hlg212.fcf.core.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import  io.github.hlg212.fcf.Constants;
import  io.github.hlg212.fcf.core.properties.CommonProperties;
import  io.github.hlg212.fcf.core.properties.ExDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

import  io.github.hlg212.fcf.core.RoutingDataSource;
import  io.github.hlg212.fcf.core.condition.DataSourceCondition;
import  io.github.hlg212.fcf.core.properties.ExDataSourceProperties;
import  io.github.hlg212.fcf.core.properties.CommonProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 初始化框架自定义数据源配置
 * @author  huangligui
 * @date 2018年10月22日
 */
@Slf4j
@Configuration
@Conditional(DataSourceCondition.class)
public class DataSourceConfig {

	@Primary
	@Bean(Constants.FRAME_BEAN_PREFIX +"dataSource")
	public DataSource dataSource(ApplicationContext applicationContext,BeanFactory beanFactory, CommonProperties properties) {
		DataSource lastDataSource = null, defaultTargetDataSource = null;
		Map<String, ExDataSourceProperties> dataSourceMap = properties.getDatasource();
		Map<Object,Object> targetDataSources = new HashMap<>();
		for(Entry<String, ExDataSourceProperties> entry : dataSourceMap.entrySet()) {
			ExDataSourceProperties dataSourceProperties = entry.getValue();
			//DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().build();
			DataSource dataSource = dataSourceProperties;
			if(dataSourceProperties.isDefault()) {
				defaultTargetDataSource = dataSource;
			} else {
				((DefaultListableBeanFactory) beanFactory).registerSingleton(entry.getKey() + "DataSource", dataSource);
			}
			log.info("register dataSource {}",entry.getKey());
			lastDataSource = dataSource;
			targetDataSources.put(entry.getKey(), dataSource);
		}
		if(defaultTargetDataSource == null) {
			defaultTargetDataSource = lastDataSource;
		}
		RoutingDataSource routingDataSource = new RoutingDataSource();
		routingDataSource.setTargetDataSources(targetDataSources);
		routingDataSource.setDefaultTargetDataSource(defaultTargetDataSource);
		return routingDataSource;
	}

}
