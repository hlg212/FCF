/**
 * 
 */
package com.hlg.fcf.core.properties;

import java.io.PrintWriter;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

import javax.management.JMException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.config.ConfigFilter;
import com.alibaba.druid.filter.encoding.EncodingConvertFilter;
import com.alibaba.druid.filter.logging.CommonsLogFilter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceStatLogger;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.pool.ExceptionSorter;
import com.alibaba.druid.pool.ValidConnectionChecker;
import com.alibaba.druid.util.Histogram;
import com.alibaba.druid.wall.WallFilter;

/**
 *
 * @author changwei
 * @date 2018年10月19日
 */
public class ExDataSourceProperties extends DruidDataSource implements InitializingBean {

	/**
	 * 是否默认使用库
	 */
	private boolean isDefault = false;

	@Autowired
	private DataSourceProperties basicProperties;

	@Override
	public void afterPropertiesSet() throws Exception {
		// if not found prefix 'spring.datasource.druid' jdbc properties
		// ,'spring.datasource' prefix jdbc properties will be used.
		if (super.getUsername() == null) {
			super.setUsername(basicProperties.determineUsername());
		}
		if (super.getPassword() == null) {
			super.setPassword(basicProperties.determinePassword());
		}
		if (super.getUrl() == null) {
			super.setUrl(basicProperties.determineUrl());
		}
		if (super.getDriverClassName() == null) {
			super.setDriverClassName(basicProperties.getDriverClassName());
		}

	}

	@Autowired(required = false)
	public void addStatFilter(StatFilter statFilter) {
		super.filters.add(statFilter);
	}

	@Autowired(required = false)
	public void addConfigFilter(ConfigFilter configFilter) {
		super.filters.add(configFilter);
	}

	@Autowired(required = false)
	public void addEncodingConvertFilter(EncodingConvertFilter encodingConvertFilter) {
		super.filters.add(encodingConvertFilter);
	}

	@Autowired(required = false)
	public void addSlf4jLogFilter(Slf4jLogFilter slf4jLogFilter) {
		super.filters.add(slf4jLogFilter);
	}

	@Autowired(required = false)
	public void addLog4jFilter(Log4jFilter log4jFilter) {
		super.filters.add(log4jFilter);
	}

	@Autowired(required = false)
	public void addLog4j2Filter(Log4j2Filter log4j2Filter) {
		super.filters.add(log4j2Filter);
	}

	@Autowired(required = false)
	public void addCommonsLogFilter(CommonsLogFilter commonsLogFilter) {
		super.filters.add(commonsLogFilter);
	}

	@Autowired(required = false)
	public void addWallFilter(WallFilter wallFilter) {
		super.filters.add(wallFilter);
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public boolean isUseLocalSessionState() {
		// TODO Auto-generated method stub
		return super.isUseLocalSessionState();
	}

	@Override
	public void setUseLocalSessionState(boolean useLocalSessionState) {
		// TODO Auto-generated method stub
		super.setUseLocalSessionState(useLocalSessionState);
	}

	@Override
	public DruidDataSourceStatLogger getStatLogger() {
		// TODO Auto-generated method stub
		return super.getStatLogger();
	}

	@Override
	public void setStatLoggerClassName(String className) {
		// TODO Auto-generated method stub
		super.setStatLoggerClassName(className);
	}

	@Override
	public void setStatLogger(DruidDataSourceStatLogger statLogger) {
		// TODO Auto-generated method stub
		super.setStatLogger(statLogger);
	}

	@Override
	public long getTimeBetweenLogStatsMillis() {
		// TODO Auto-generated method stub
		return super.getTimeBetweenLogStatsMillis();
	}

	@Override
	public void setTimeBetweenLogStatsMillis(long timeBetweenLogStatsMillis) {
		// TODO Auto-generated method stub
		super.setTimeBetweenLogStatsMillis(timeBetweenLogStatsMillis);
	}

	@Override
	public boolean isOracle() {
		// TODO Auto-generated method stub
		return super.isOracle();
	}

	@Override
	public void setOracle(boolean isOracle) {
		// TODO Auto-generated method stub
		super.setOracle(isOracle);
	}

	@Override
	public boolean isUseUnfairLock() {
		// TODO Auto-generated method stub
		return super.isUseUnfairLock();
	}

	@Override
	public void setUseUnfairLock(boolean useUnfairLock) {
		// TODO Auto-generated method stub
		super.setUseUnfairLock(useUnfairLock);
	}

	@Override
	public boolean isUseOracleImplicitCache() {
		// TODO Auto-generated method stub
		return super.isUseOracleImplicitCache();
	}

	@Override
	public void setUseOracleImplicitCache(boolean useOracleImplicitCache) {
		// TODO Auto-generated method stub
		super.setUseOracleImplicitCache(useOracleImplicitCache);
	}

	@Override
	public Throwable getLastCreateError() {
		// TODO Auto-generated method stub
		return super.getLastCreateError();
	}

	@Override
	public Throwable getLastError() {
		// TODO Auto-generated method stub
		return super.getLastError();
	}

	@Override
	public long getLastErrorTimeMillis() {
		// TODO Auto-generated method stub
		return super.getLastErrorTimeMillis();
	}

	@Override
	public Date getLastErrorTime() {
		// TODO Auto-generated method stub
		return super.getLastErrorTime();
	}

	@Override
	public long getLastCreateErrorTimeMillis() {
		// TODO Auto-generated method stub
		return super.getLastCreateErrorTimeMillis();
	}

	@Override
	public Date getLastCreateErrorTime() {
		// TODO Auto-generated method stub
		return super.getLastCreateErrorTime();
	}

	@Override
	public int getTransactionQueryTimeout() {
		// TODO Auto-generated method stub
		return super.getTransactionQueryTimeout();
	}

	@Override
	public void setTransactionQueryTimeout(int transactionQueryTimeout) {
		// TODO Auto-generated method stub
		super.setTransactionQueryTimeout(transactionQueryTimeout);
	}

	@Override
	public long getExecuteCount() {
		// TODO Auto-generated method stub
		return super.getExecuteCount();
	}

	@Override
	public boolean isDupCloseLogEnable() {
		// TODO Auto-generated method stub
		return super.isDupCloseLogEnable();
	}

	@Override
	public void setDupCloseLogEnable(boolean dupCloseLogEnable) {
		// TODO Auto-generated method stub
		super.setDupCloseLogEnable(dupCloseLogEnable);
	}

	@Override
	public ObjectName getObjectName() {
		// TODO Auto-generated method stub
		return super.getObjectName();
	}

	@Override
	public void setObjectName(ObjectName objectName) {
		// TODO Auto-generated method stub
		super.setObjectName(objectName);
	}

	@Override
	public Histogram getTransactionHistogram() {
		// TODO Auto-generated method stub
		return super.getTransactionHistogram();
	}

	@Override
	public long getCachedPreparedStatementMissCount() {
		// TODO Auto-generated method stub
		return super.getCachedPreparedStatementMissCount();
	}

	@Override
	public long getCachedPreparedStatementAccessCount() {
		// TODO Auto-generated method stub
		return super.getCachedPreparedStatementAccessCount();
	}

	@Override
	public long getCachedPreparedStatementDeleteCount() {
		// TODO Auto-generated method stub
		return super.getCachedPreparedStatementDeleteCount();
	}

	@Override
	public long getCachedPreparedStatementCount() {
		// TODO Auto-generated method stub
		return super.getCachedPreparedStatementCount();
	}

	@Override
	public long getClosedPreparedStatementCount() {
		// TODO Auto-generated method stub
		return super.getClosedPreparedStatementCount();
	}

	@Override
	public long getPreparedStatementCount() {
		// TODO Auto-generated method stub
		return super.getPreparedStatementCount();
	}

	@Override
	public long getCachedPreparedStatementHitCount() {
		// TODO Auto-generated method stub
		return super.getCachedPreparedStatementHitCount();
	}

	@Override
	public long getTransactionThresholdMillis() {
		// TODO Auto-generated method stub
		return super.getTransactionThresholdMillis();
	}

	@Override
	public void setTransactionThresholdMillis(long transactionThresholdMillis) {
		// TODO Auto-generated method stub
		super.setTransactionThresholdMillis(transactionThresholdMillis);
	}

	@Override
	public long[] getTransactionHistogramValues() {
		// TODO Auto-generated method stub
		return super.getTransactionHistogramValues();
	}

	@Override
	public long[] getTransactionHistogramRanges() {
		// TODO Auto-generated method stub
		return super.getTransactionHistogramRanges();
	}

	@Override
	public long getCommitCount() {
		// TODO Auto-generated method stub
		return super.getCommitCount();
	}

	@Override
	public long getRollbackCount() {
		// TODO Auto-generated method stub
		return super.getRollbackCount();
	}

	@Override
	public long getStartTransactionCount() {
		// TODO Auto-generated method stub
		return super.getStartTransactionCount();
	}

	@Override
	public boolean isBreakAfterAcquireFailure() {
		// TODO Auto-generated method stub
		return super.isBreakAfterAcquireFailure();
	}

	@Override
	public void setBreakAfterAcquireFailure(boolean breakAfterAcquireFailure) {
		// TODO Auto-generated method stub
		super.setBreakAfterAcquireFailure(breakAfterAcquireFailure);
	}

	@Override
	public int getConnectionErrorRetryAttempts() {
		// TODO Auto-generated method stub
		return super.getConnectionErrorRetryAttempts();
	}

	@Override
	public void setConnectionErrorRetryAttempts(int connectionErrorRetryAttempts) {
		// TODO Auto-generated method stub
		super.setConnectionErrorRetryAttempts(connectionErrorRetryAttempts);
	}

	@Override
	public long getDupCloseCount() {
		// TODO Auto-generated method stub
		return super.getDupCloseCount();
	}

	@Override
	public int getMaxPoolPreparedStatementPerConnectionSize() {
		// TODO Auto-generated method stub
		return super.getMaxPoolPreparedStatementPerConnectionSize();
	}

	@Override
	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		// TODO Auto-generated method stub
		super.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
	}

	@Override
	public boolean isSharePreparedStatements() {
		// TODO Auto-generated method stub
		return super.isSharePreparedStatements();
	}

	@Override
	public void setSharePreparedStatements(boolean sharePreparedStatements) {
		// TODO Auto-generated method stub
		super.setSharePreparedStatements(sharePreparedStatements);
	}

	@Override
	public ValidConnectionChecker getValidConnectionChecker() {
		// TODO Auto-generated method stub
		return super.getValidConnectionChecker();
	}

	@Override
	public void setValidConnectionChecker(ValidConnectionChecker validConnectionChecker) {
		// TODO Auto-generated method stub
		super.setValidConnectionChecker(validConnectionChecker);
	}

	@Override
	public String getValidConnectionCheckerClassName() {
		// TODO Auto-generated method stub
		return super.getValidConnectionCheckerClassName();
	}

	@Override
	public void setValidConnectionCheckerClassName(String validConnectionCheckerClass) throws Exception {
		// TODO Auto-generated method stub
		super.setValidConnectionCheckerClassName(validConnectionCheckerClass);
	}

	@Override
	public String getDbType() {
		// TODO Auto-generated method stub
		return super.getDbType();
	}

	@Override
	public void setDbType(String dbType) {
		// TODO Auto-generated method stub
		super.setDbType(dbType);
	}

	@Override
	public Collection<String> getConnectionInitSqls() {
		// TODO Auto-generated method stub
		return super.getConnectionInitSqls();
	}

	@Override
	public void setConnectionInitSqls(Collection<? extends Object> connectionInitSqls) {
		// TODO Auto-generated method stub
		super.setConnectionInitSqls(connectionInitSqls);
	}

	@Override
	public long getTimeBetweenConnectErrorMillis() {
		// TODO Auto-generated method stub
		return super.getTimeBetweenConnectErrorMillis();
	}

	@Override
	public void setTimeBetweenConnectErrorMillis(long timeBetweenConnectErrorMillis) {
		// TODO Auto-generated method stub
		super.setTimeBetweenConnectErrorMillis(timeBetweenConnectErrorMillis);
	}

	@Override
	public int getMaxOpenPreparedStatements() {
		// TODO Auto-generated method stub
		return super.getMaxOpenPreparedStatements();
	}

	@Override
	public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
		// TODO Auto-generated method stub
		super.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
	}

	@Override
	public boolean isLogAbandoned() {
		// TODO Auto-generated method stub
		return super.isLogAbandoned();
	}

	@Override
	public void setLogAbandoned(boolean logAbandoned) {
		// TODO Auto-generated method stub
		super.setLogAbandoned(logAbandoned);
	}

	@Override
	public int getRemoveAbandonedTimeout() {
		// TODO Auto-generated method stub
		return super.getRemoveAbandonedTimeout();
	}

	@Override
	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
		// TODO Auto-generated method stub
		super.setRemoveAbandonedTimeout(removeAbandonedTimeout);
	}

	@Override
	public void setRemoveAbandonedTimeoutMillis(long removeAbandonedTimeoutMillis) {
		// TODO Auto-generated method stub
		super.setRemoveAbandonedTimeoutMillis(removeAbandonedTimeoutMillis);
	}

	@Override
	public long getRemoveAbandonedTimeoutMillis() {
		// TODO Auto-generated method stub
		return super.getRemoveAbandonedTimeoutMillis();
	}

	@Override
	public boolean isRemoveAbandoned() {
		// TODO Auto-generated method stub
		return super.isRemoveAbandoned();
	}

	@Override
	public void setRemoveAbandoned(boolean removeAbandoned) {
		// TODO Auto-generated method stub
		super.setRemoveAbandoned(removeAbandoned);
	}

	@Override
	public long getMinEvictableIdleTimeMillis() {
		// TODO Auto-generated method stub
		return super.getMinEvictableIdleTimeMillis();
	}

	@Override
	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		// TODO Auto-generated method stub
		super.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
	}

	@Override
	public long getMaxEvictableIdleTimeMillis() {
		// TODO Auto-generated method stub
		return super.getMaxEvictableIdleTimeMillis();
	}

	@Override
	public void setMaxEvictableIdleTimeMillis(long maxEvictableIdleTimeMillis) {
		// TODO Auto-generated method stub
		super.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);
	}

	@Override
	public long getPhyTimeoutMillis() {
		// TODO Auto-generated method stub
		return super.getPhyTimeoutMillis();
	}

	@Override
	public void setPhyTimeoutMillis(long phyTimeoutMillis) {
		// TODO Auto-generated method stub
		super.setPhyTimeoutMillis(phyTimeoutMillis);
	}

	@Override
	public int getNumTestsPerEvictionRun() {
		// TODO Auto-generated method stub
		return super.getNumTestsPerEvictionRun();
	}

	@Override
	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
		// TODO Auto-generated method stub
		super.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
	}

	@Override
	public long getTimeBetweenEvictionRunsMillis() {
		// TODO Auto-generated method stub
		return super.getTimeBetweenEvictionRunsMillis();
	}

	@Override
	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		// TODO Auto-generated method stub
		super.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
	}

	@Override
	public int getMaxWaitThreadCount() {
		// TODO Auto-generated method stub
		return super.getMaxWaitThreadCount();
	}

	@Override
	public void setMaxWaitThreadCount(int maxWaithThreadCount) {
		// TODO Auto-generated method stub
		super.setMaxWaitThreadCount(maxWaithThreadCount);
	}

	@Override
	public String getValidationQuery() {
		// TODO Auto-generated method stub
		return super.getValidationQuery();
	}

	@Override
	public void setValidationQuery(String validationQuery) {
		// TODO Auto-generated method stub
		super.setValidationQuery(validationQuery);
	}

	@Override
	public int getValidationQueryTimeout() {
		// TODO Auto-generated method stub
		return super.getValidationQueryTimeout();
	}

	@Override
	public void setValidationQueryTimeout(int validationQueryTimeout) {
		// TODO Auto-generated method stub
		super.setValidationQueryTimeout(validationQueryTimeout);
	}

	@Override
	public boolean isAccessToUnderlyingConnectionAllowed() {
		// TODO Auto-generated method stub
		return super.isAccessToUnderlyingConnectionAllowed();
	}

	@Override
	public void setAccessToUnderlyingConnectionAllowed(boolean accessToUnderlyingConnectionAllowed) {
		// TODO Auto-generated method stub
		super.setAccessToUnderlyingConnectionAllowed(accessToUnderlyingConnectionAllowed);
	}

	@Override
	public boolean isTestOnBorrow() {
		// TODO Auto-generated method stub
		return super.isTestOnBorrow();
	}

	@Override
	public void setTestOnBorrow(boolean testOnBorrow) {
		// TODO Auto-generated method stub
		super.setTestOnBorrow(testOnBorrow);
	}

	@Override
	public boolean isTestOnReturn() {
		// TODO Auto-generated method stub
		return super.isTestOnReturn();
	}

	@Override
	public void setTestOnReturn(boolean testOnReturn) {
		// TODO Auto-generated method stub
		super.setTestOnReturn(testOnReturn);
	}

	@Override
	public boolean isTestWhileIdle() {
		// TODO Auto-generated method stub
		return super.isTestWhileIdle();
	}

	@Override
	public void setTestWhileIdle(boolean testWhileIdle) {
		// TODO Auto-generated method stub
		super.setTestWhileIdle(testWhileIdle);
	}

	@Override
	public boolean isDefaultAutoCommit() {
		// TODO Auto-generated method stub
		return super.isDefaultAutoCommit();
	}

	@Override
	public void setDefaultAutoCommit(boolean defaultAutoCommit) {
		// TODO Auto-generated method stub
		super.setDefaultAutoCommit(defaultAutoCommit);
	}

	@Override
	public Boolean getDefaultReadOnly() {
		// TODO Auto-generated method stub
		return super.getDefaultReadOnly();
	}

	@Override
	public void setDefaultReadOnly(Boolean defaultReadOnly) {
		// TODO Auto-generated method stub
		super.setDefaultReadOnly(defaultReadOnly);
	}

	@Override
	public Integer getDefaultTransactionIsolation() {
		// TODO Auto-generated method stub
		return super.getDefaultTransactionIsolation();
	}

	@Override
	public void setDefaultTransactionIsolation(Integer defaultTransactionIsolation) {
		// TODO Auto-generated method stub
		super.setDefaultTransactionIsolation(defaultTransactionIsolation);
	}

	@Override
	public String getDefaultCatalog() {
		// TODO Auto-generated method stub
		return super.getDefaultCatalog();
	}

	@Override
	public void setDefaultCatalog(String defaultCatalog) {
		// TODO Auto-generated method stub
		super.setDefaultCatalog(defaultCatalog);
	}

	@Override
	public PasswordCallback getPasswordCallback() {
		// TODO Auto-generated method stub
		return super.getPasswordCallback();
	}

	@Override
	public void setPasswordCallback(PasswordCallback passwordCallback) {
		// TODO Auto-generated method stub
		super.setPasswordCallback(passwordCallback);
	}

	@Override
	public void setPasswordCallbackClassName(String passwordCallbackClassName) throws Exception {
		// TODO Auto-generated method stub
		super.setPasswordCallbackClassName(passwordCallbackClassName);
	}

	@Override
	public NameCallback getUserCallback() {
		// TODO Auto-generated method stub
		return super.getUserCallback();
	}

	@Override
	public void setUserCallback(NameCallback userCallback) {
		// TODO Auto-generated method stub
		super.setUserCallback(userCallback);
	}

	@Override
	public boolean isInitVariants() {
		// TODO Auto-generated method stub
		return super.isInitVariants();
	}

	@Override
	public void setInitVariants(boolean initVariants) {
		// TODO Auto-generated method stub
		super.setInitVariants(initVariants);
	}

	@Override
	public boolean isInitGlobalVariants() {
		// TODO Auto-generated method stub
		return super.isInitGlobalVariants();
	}

	@Override
	public void setInitGlobalVariants(boolean initGlobalVariants) {
		// TODO Auto-generated method stub
		super.setInitGlobalVariants(initGlobalVariants);
	}

	@Override
	public int getQueryTimeout() {
		// TODO Auto-generated method stub
		return super.getQueryTimeout();
	}

	@Override
	public void setQueryTimeout(int seconds) {
		// TODO Auto-generated method stub
		super.setQueryTimeout(seconds);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		super.setName(name);
	}

	@Override
	public boolean isPoolPreparedStatements() {
		// TODO Auto-generated method stub
		return super.isPoolPreparedStatements();
	}

	@Override
	public long getMaxWait() {
		// TODO Auto-generated method stub
		return super.getMaxWait();
	}

	@Override
	public void setMaxWait(long maxWaitMillis) {
		// TODO Auto-generated method stub
		super.setMaxWait(maxWaitMillis);
	}

	@Override
	public int getNotFullTimeoutRetryCount() {
		// TODO Auto-generated method stub
		return super.getNotFullTimeoutRetryCount();
	}

	@Override
	public void setNotFullTimeoutRetryCount(int notFullTimeoutRetryCount) {
		// TODO Auto-generated method stub
		super.setNotFullTimeoutRetryCount(notFullTimeoutRetryCount);
	}

	@Override
	public int getMinIdle() {
		// TODO Auto-generated method stub
		return super.getMinIdle();
	}

	@Override
	public void setMinIdle(int value) {
		// TODO Auto-generated method stub
		super.setMinIdle(value);
	}

	@Override
	public int getMaxIdle() {
		// TODO Auto-generated method stub
		return super.getMaxIdle();
	}

	@Override
	public void setMaxIdle(int maxIdle) {
		// TODO Auto-generated method stub
		super.setMaxIdle(maxIdle);
	}

	@Override
	public int getInitialSize() {
		// TODO Auto-generated method stub
		return super.getInitialSize();
	}

	@Override
	public void setInitialSize(int initialSize) {
		// TODO Auto-generated method stub
		super.setInitialSize(initialSize);
	}

	@Override
	public long getCreateErrorCount() {
		// TODO Auto-generated method stub
		return super.getCreateErrorCount();
	}

	@Override
	public int getMaxActive() {
		// TODO Auto-generated method stub
		return super.getMaxActive();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return super.getUsername();
	}

	@Override
	public void setUsername(String username) {
		// TODO Auto-generated method stub
		super.setUsername(username);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return super.getPassword();
	}

	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		super.setPassword(password);
	}

	@Override
	public Properties getConnectProperties() {
		// TODO Auto-generated method stub
		return super.getConnectProperties();
	}

	@Override
	public void setConnectionProperties(String connectionProperties) {
		// TODO Auto-generated method stub
		super.setConnectionProperties(connectionProperties);
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return super.getUrl();
	}

	@Override
	public String getRawJdbcUrl() {
		// TODO Auto-generated method stub
		return super.getRawJdbcUrl();
	}

	@Override
	public void setUrl(String jdbcUrl) {
		// TODO Auto-generated method stub
		super.setUrl(jdbcUrl);
	}

	@Override
	public String getDriverClassName() {
		// TODO Auto-generated method stub
		return super.getDriverClassName();
	}

	@Override
	public void setDriverClassName(String driverClass) {
		// TODO Auto-generated method stub
		super.setDriverClassName(driverClass);
	}

	@Override
	public ClassLoader getDriverClassLoader() {
		// TODO Auto-generated method stub
		return super.getDriverClassLoader();
	}

	@Override
	public void setDriverClassLoader(ClassLoader driverClassLoader) {
		// TODO Auto-generated method stub
		super.setDriverClassLoader(driverClassLoader);
	}

	@Override
	public PrintWriter getLogWriter() {
		// TODO Auto-generated method stub
		return super.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		super.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) {
		// TODO Auto-generated method stub
		super.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() {
		// TODO Auto-generated method stub
		return super.getLoginTimeout();
	}

	@Override
	public Driver getDriver() {
		// TODO Auto-generated method stub
		return super.getDriver();
	}

	@Override
	public void setDriver(Driver driver) {
		// TODO Auto-generated method stub
		super.setDriver(driver);
	}

	@Override
	public int getDriverMajorVersion() {
		// TODO Auto-generated method stub
		return super.getDriverMajorVersion();
	}

	@Override
	public int getDriverMinorVersion() {
		// TODO Auto-generated method stub
		return super.getDriverMinorVersion();
	}

	@Override
	public ExceptionSorter getExceptionSorter() {
		// TODO Auto-generated method stub
		return super.getExceptionSorter();
	}

	@Override
	public String getExceptionSorterClassName() {
		// TODO Auto-generated method stub
		return super.getExceptionSorterClassName();
	}

	@Override
	public void setExceptionSorter(ExceptionSorter exceptionSoter) {
		// TODO Auto-generated method stub
		super.setExceptionSorter(exceptionSoter);
	}

	@Override
	public void setExceptionSorterClassName(String exceptionSorter) throws Exception {
		// TODO Auto-generated method stub
		super.setExceptionSorterClassName(exceptionSorter);
	}

	@Override
	public void setExceptionSorter(String exceptionSorter) throws SQLException {
		// TODO Auto-generated method stub
		super.setExceptionSorter(exceptionSorter);
	}

	@Override
	public List<Filter> getProxyFilters() {
		// TODO Auto-generated method stub
		return super.getProxyFilters();
	}

	@Override
	public void setProxyFilters(List<Filter> filters) {
		// TODO Auto-generated method stub
		super.setProxyFilters(filters);
	}

	@Override
	public String[] getFilterClasses() {
		// TODO Auto-generated method stub
		return super.getFilterClasses();
	}

	@Override
	public void setFilters(String filters) throws SQLException {
		// TODO Auto-generated method stub
		super.setFilters(filters);
	}

	@Override
	public Set<DruidPooledConnection> getActiveConnections() {
		// TODO Auto-generated method stub
		return super.getActiveConnections();
	}

	@Override
	public List<String> getActiveConnectionStackTrace() {
		// TODO Auto-generated method stub
		return super.getActiveConnectionStackTrace();
	}

	@Override
	public long getCreateTimespanNano() {
		// TODO Auto-generated method stub
		return super.getCreateTimespanNano();
	}

	@Override
	public long getCreateTimespanMillis() {
		// TODO Auto-generated method stub
		return super.getCreateTimespanMillis();
	}

	@Override
	public Driver getRawDriver() {
		// TODO Auto-generated method stub
		return super.getRawDriver();
	}

	@Override
	public boolean isClearFiltersEnable() {
		// TODO Auto-generated method stub
		return super.isClearFiltersEnable();
	}

	@Override
	public void setClearFiltersEnable(boolean clearFiltersEnable) {
		// TODO Auto-generated method stub
		super.setClearFiltersEnable(clearFiltersEnable);
	}

	@Override
	protected void setCreateError(Throwable ex) {
		// TODO Auto-generated method stub
		super.setCreateError(ex);
	}

	@Override
	protected void setFailContinuous(boolean fail) {
		// TODO Auto-generated method stub
		super.setFailContinuous(fail);
	}

	@Override
	public CompositeDataSupport getCompositeData() throws JMException {
		// TODO Auto-generated method stub
		return super.getCompositeData();
	}

	@Override
	public long getID() {
		// TODO Auto-generated method stub
		return super.getID();
	}

	@Override
	public Date getCreatedTime() {
		// TODO Auto-generated method stub
		return super.getCreatedTime();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return super.getParentLogger();
	}

	@Override
	public boolean isAsyncCloseConnectionEnable() {
		// TODO Auto-generated method stub
		return super.isAsyncCloseConnectionEnable();
	}

	@Override
	public void setAsyncCloseConnectionEnable(boolean asyncCloseConnectionEnable) {
		// TODO Auto-generated method stub
		super.setAsyncCloseConnectionEnable(asyncCloseConnectionEnable);
	}

	@Override
	public ScheduledExecutorService getCreateScheduler() {
		// TODO Auto-generated method stub
		return super.getCreateScheduler();
	}

	@Override
	public void setCreateScheduler(ScheduledExecutorService createScheduler) {
		// TODO Auto-generated method stub
		super.setCreateScheduler(createScheduler);
	}

	@Override
	public ScheduledExecutorService getDestroyScheduler() {
		// TODO Auto-generated method stub
		return super.getDestroyScheduler();
	}

	@Override
	public void setDestroyScheduler(ScheduledExecutorService destroyScheduler) {
		// TODO Auto-generated method stub
		super.setDestroyScheduler(destroyScheduler);
	}

	@Override
	public boolean isInited() {
		// TODO Auto-generated method stub
		return super.isInited();
	}

	@Override
	public int getMaxCreateTaskCount() {
		// TODO Auto-generated method stub
		return super.getMaxCreateTaskCount();
	}

	@Override
	public void setMaxCreateTaskCount(int maxCreateTaskCount) {
		// TODO Auto-generated method stub
		super.setMaxCreateTaskCount(maxCreateTaskCount);
	}

	@Override
	public boolean isFailFast() {
		// TODO Auto-generated method stub
		return super.isFailFast();
	}

	@Override
	public void setFailFast(boolean failFast) {
		// TODO Auto-generated method stub
		super.setFailFast(failFast);
	}
}
