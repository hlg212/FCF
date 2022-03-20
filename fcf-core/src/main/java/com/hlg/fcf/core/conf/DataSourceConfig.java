/**
 * 
 */
package com.hlg.fcf.core.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import com.hlg.fcf.Constants;
import com.hlg.fcf.core.properties.CommonProperties;
import com.hlg.fcf.core.properties.ExDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

import com.hlg.fcf.core.RoutingDataSource;
import com.hlg.fcf.core.condition.DataSourceCondition;
import com.hlg.fcf.core.properties.ExDataSourceProperties;
import com.hlg.fcf.core.properties.CommonProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 初始化框架自定义数据源配置
 * @author changwei
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
