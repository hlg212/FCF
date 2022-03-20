/**
 * 
 */
package com.hlg.fcf.core;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.hlg.fcf.constants.FrameCommonConstants;
import com.hlg.fcf.util.ThreadLocalHelper;

/**
 *
 * @author changwei
 * @date 2018年10月26日
 */
public class RoutingDataSource extends AbstractRoutingDataSource{
	
	@Override
	protected Object determineCurrentLookupKey() {
		return ThreadLocalHelper.get(FrameCommonConstants.DYNAMIC_DATA_SOURCE_KEY);
	}

}
