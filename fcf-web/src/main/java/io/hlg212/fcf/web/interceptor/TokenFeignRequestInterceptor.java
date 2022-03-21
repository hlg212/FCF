/**
 * 
 */
package io.hlg212.fcf.web.interceptor;

import io.hlg212.fcf.web.util.TokenHelper;
import jdk.nashorn.internal.parser.Token;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.hlg212.fcf.constants.FrameCommonConstants;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Iterator;
import java.util.Map;

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
public class TokenFeignRequestInterceptor implements RequestInterceptor{


	public static final String AUTHORIZATION_KEY = "Authorization";

	@Override
	public void apply(RequestTemplate template) {
		template.header(FrameCommonConstants.FEIGN_REQUEST_KEY, "true");

		boolean apdToken = true;
		Iterator<String> iterable = template.headers().keySet().iterator();
		while(  iterable.hasNext() )
		{
			if( iterable.next().equalsIgnoreCase(AUTHORIZATION_KEY) )
			{
				apdToken = false;
				break;
			}
		}
		if( apdToken ) {
			String token = TokenHelper.getBearerToken();
			if (StringUtils.isNotEmpty(token)) {
				template.header(AUTHORIZATION_KEY, token);
			}
		}
	}

}
