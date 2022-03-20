/** 
 * Project Name:service-jcj 
 * File Name:FworkContextFilter.java 
 * Package Name:com.hlg.fcf.web.config 
 * Date:2017年8月23日 上午9:10:42 
 * Copyright (c) 2017, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package com.hlg.fcf.web.filter;

import com.hlg.fcf.context.TokenContext;
import com.hlg.fcf.web.annotation.MvcConditional;
import com.hlg.fcf.web.util.HttpServletHelper;
import com.hlg.fcf.web.util.TokenHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/** 
 * ClassName: TokenFilter
 * Function: TODO ADD FUNCTION. 
 * Reason: TODO ADD REASON(可选).
 * date: 2017年8月23日 上午9:10:42
 * 
 * @author huangligui 
 */

public class TokenFilter implements Filter {

	private MvcTokenContext mvcTokenContext = new MvcTokenContext();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
		TokenHelper.setContext(mvcTokenContext);
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Component
	@MvcConditional
	@Primary
	public static class MvcTokenContext implements TokenContext
	{
		public static final String BEARER = "bearer ";
		public static final String TOKEN_KEY = "Authorization";
		public static final String PARAM_TOKEN_KEY = "__AuthToken";
		@Override
		public String getToken() {

			String token = extractToken(HttpServletHelper.getRequest());
			if( StringUtils.isEmpty(token) )
			{
				Cookie cks[] = HttpServletHelper.getRequest().getCookies();
				if( cks != null ) {
					for (int i = 0; i < cks.length; i++) {
						if (TOKEN_KEY.equalsIgnoreCase(cks[i].getName())) {
							token = cks[i].getValue();
							break;
						}
					}
				}
				if( StringUtils.isEmpty(token) )
				{
					token = HttpServletHelper.getRequest().getParameter(PARAM_TOKEN_KEY);
				}
			}
			return token;
		}


		private String extractToken(HttpServletRequest request) {
			String token = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (StringUtils.isBlank(token) || !token.toLowerCase().startsWith(BEARER)) {
				return null;
			}
			return token.substring(BEARER.length());
		}
	}

}
