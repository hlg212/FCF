/**
 * 
 */
package io.hlg212.fcf.web.interceptor;

import io.hlg212.fcf.web.util.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
public class ApiSecretRequestInterceptor implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {
		template.header(Constants.FEIGN_API_SECRET_KEY, Constants.FEIGN_API_SECRET_VALUE);
	}

}
