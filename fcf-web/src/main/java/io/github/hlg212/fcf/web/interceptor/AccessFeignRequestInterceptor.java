/**
 * 
 */
package  io.github.hlg212.fcf.web.interceptor;

import  io.github.hlg212.fcf.constants.FrameCommonConstants;
import  io.github.hlg212.fcf.util.AccessContextHelper;
import  io.github.hlg212.fcf.web.util.TokenHelper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * FeignClient接口请求拦截器
 * 主要在header中添加一些公用的需要传递的信息
 * 比如，登录 token
 *
 * @author huangligui
 * @date 2018年12月26日
 */
@Component
@Order
public class AccessFeignRequestInterceptor implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {

		String id = AccessContextHelper.getAccessId();

		if( StringUtils.isNotEmpty(id) ) {
			template.header(FrameCommonConstants.ACCESS_ID, id);
		}
	}

}
