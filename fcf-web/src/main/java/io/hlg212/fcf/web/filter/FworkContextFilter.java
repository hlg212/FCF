/** 
 * Project Name:service-jcj 
 * File Name:FworkContextFilter.java 
 * Package Name:io.hlg212.fcf.web.config
 * Date:2017年8月23日 上午9:10:42 
 * Copyright (c) 2017, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package io.hlg212.fcf.web.filter;

import io.hlg212.fcf.api.UserInfoApi;
import io.hlg212.fcf.context.FworkContext;
import io.hlg212.fcf.model.basic.IUser;
import io.hlg212.fcf.model.basic.User;
import io.hlg212.fcf.util.FworkHelper;
import io.hlg212.fcf.util.ThreadLocalHelper;
import io.hlg212.fcf.web.util.HttpServletHelper;
import io.hlg212.fcf.web.util.TokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/** 
 * ClassName: FworkContextFilter
 * Function: TODO ADD FUNCTION. 
 * Reason: TODO ADD REASON(可选).
 * date: 2017年8月23日 上午9:10:42
 * 
 * @author huangligui 
 */

@Slf4j
public class FworkContextFilter implements Filter {


	private HttpFworkContext httpFworkContext = new HttpFworkContext();
	@Autowired
	private UserInfoApi userInfoApi;


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
		FworkHelper.setContext(httpFworkContext);
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	private class HttpFworkContext implements FworkContext
	{
		private final static String FWORKCONTEXT_ATTRIBUTES = "FWORKCONTEXT_ATTRIBUTES";

		private final static String HEADER_AUTHORIZATION = "authorization";

		@Override
		public IUser getUser() {
			// TODO Auto-generated method stub
			String bearerAuthorization = TokenHelper.getBearerToken();
			IUser u = null;
			try {
				u = userInfoApi.userinfo(bearerAuthorization);
			}catch (Exception e)
			{
				log.debug("获取用户信息失败 ，token:{}",bearerAuthorization);
			}
			return u;
		}

		@Override
		public String getUid() {
			// TODO Auto-generated method stub
			IUser s = getUser();
			if( s != null )
			{
				return s.getId();
			}
			return null;
		}

		@Override
		public Object get(String key) {
			// TODO Auto-generated method stub
			return getAttributes().get(key);
		}
		
		private Map getAttributes()
		{
			Map m = (Map)ThreadLocalHelper.get(FWORKCONTEXT_ATTRIBUTES);
			if( m == null )
			{
				m = new HashMap();
				ThreadLocalHelper.set(FWORKCONTEXT_ATTRIBUTES, m);
				
			}
			return m;
		}

		@Override
		public void put(String key, Object o) {
			// TODO Auto-generated method stub
			getAttributes().put(key, o);
		}
		

	}

}
