package io.hlg212.fcf.core.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionProperties;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.AbstractTransactionManagementConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.SimpleTransactionStatus;

import javax.sql.DataSource;


@ConditionalOnClass(PlatformTransactionManager.class)
@AutoConfigureAfter({ JtaAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		Neo4jDataAutoConfiguration.class,TransactionAutoConfiguration.class })
@EnableConfigurationProperties(TransactionProperties.class)
public class ExTransactionAutoConfiguration {

	@Configuration
	@ConditionalOnBean(PlatformTransactionManager.class)
	@ConditionalOnMissingBean(AbstractTransactionManagementConfiguration.class)
	public static class EnableTransactionManagementConfiguration {

		@Configuration
		@EnableTransactionManagement(proxyTargetClass = false,order=100)
		@ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "false", matchIfMissing = false)
		public static class JdkDynamicAutoProxyConfiguration {

		}

		@Configuration
		@EnableTransactionManagement(proxyTargetClass = true,order=100)
		@ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "true", matchIfMissing = true)
		public static class CglibAutoProxyConfiguration {

		}

	}

	@Bean
	@ConditionalOnMissingBean(value={PlatformTransactionManager.class})
	public PlatformTransactionManager noopPlatformTransactionManager()
	{
		return new NoopPlatformTransactionManager();
	}

	@Slf4j
	static class NoopPlatformTransactionManager implements PlatformTransactionManager
	{

		@Override
		public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
			log.debug(" NoopPlatformTransactionManager ");

			return new SimpleTransactionStatus();
		}

		@Override
		public void commit(TransactionStatus status) throws TransactionException {
			log.debug(" NoopPlatformTransactionManager commit");
		}

		@Override
		public void rollback(TransactionStatus status) throws TransactionException {
			log.debug(" NoopPlatformTransactionManager rollback");
		}
	}
}
