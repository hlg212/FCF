package io.hlg212.fcf.core;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import io.hlg212.fcf.constants.FrameCommonConstants;
import io.hlg212.fcf.util.ThreadLocalHelper;

/**
 *
 * @author huangligui
 * @date 2018年10月26日
 */
public class RoutingDataSource extends AbstractRoutingDataSource{
	
	@Override
	protected Object determineCurrentLookupKey() {
		return ThreadLocalHelper.get(FrameCommonConstants.DYNAMIC_DATA_SOURCE_KEY);
	}

}
