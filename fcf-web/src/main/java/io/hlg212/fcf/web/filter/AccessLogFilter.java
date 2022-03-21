package io.hlg212.fcf.web.filter;

import com.alibaba.fastjson.JSON;
import io.hlg212.fcf.constants.DataConstants;
import io.hlg212.fcf.constants.FrameCommonConstants;
import io.hlg212.fcf.context.AccessContext;
import io.hlg212.fcf.log.AccessLogWrite;
import io.hlg212.fcf.model.basic.IUser;
import io.hlg212.fcf.model.log.AccessLog;
import io.hlg212.fcf.properties.AccessLogProperties;
import io.hlg212.fcf.util.*;
import io.hlg212.fcf.web.util.HttpServletHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/** 
 * ClassName: AccessLogFilter
 * Function:  记录访问日志
 * Reason: TODO ADD REASON(可选).
 * date: 2019年5月6日 上午9:10:42
 * 
 * @author huangligui 
 */

@Slf4j
public class AccessLogFilter implements Filter {

	public static final String REQUEST_BODY_PARAM = "REQUEST_BODY_PARAM";

	@Autowired
	private AccessLogWrite accessLogWrite;

	@Autowired
	private AccessLogProperties accessLogProperties;

	private AccessContext  accessContext = new HttpAccessContext();

	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
		AccessLog alog = null;
		Exception ex = null;
		AccessContextHelper.setContext(accessContext);
		try
		{
			String method = HttpServletHelper.getRequest().getMethod();
			if("OPTIONS".equals(method)) {
				chain.doFilter(request, response);
				return;
			}
			String first = HttpServletHelper.getRequest().getHeader(FrameCommonConstants.FEIGN_REQUEST_KEY);
			String uri = HttpServletHelper.getRequest().getRequestURI();
			if( StringUtils.isEmpty(first) && accessLogProperties.getEnable()  && !isIgnore(uri) )
			{
				alog = create();
				HttpServletHelper.getRequest().setAttribute(FrameCommonConstants.ACCESS_ID,alog.getId());
			}
			chain.doFilter(request, response);
		}
		catch (Exception e)
		{
			ex = e;
			throw e;
		}
		finally {
			if( alog != null )
			{
				if( ex == null )
				{
					ex = (Exception) HttpServletHelper.getRequest().getAttribute(FrameCommonConstants.REQUEST_EXCEPTION_KEY);
				}
				afterSet(alog,ex);
				try {
					if( accessLogProperties.getOnlySaveException() )
					{
						if( ex != null )
						{
							accessLogWrite.write(alog);
						}
					}else{
						accessLogWrite.write(alog);
					}
				}catch (Exception e)
				{
					log.error("访问日志记录失败!",e);
				}
			}
		}

	}

	public boolean isIgnore(String uri)
	{
		if( accessLogProperties.getIgnores() != null )
		{
			for( String a : accessLogProperties.getIgnores() )
			{
				if(antPathMatcher.match(a,uri)){
					return true;
				}
			}
		}
		return false;
	}

	private void afterSet(AccessLog log,Exception ex)
	{
		log.setEndTime( new Date() );
		long duration =  log.getEndTime().getTime() -  log.getStartTime().getTime();
		log.setDuration(duration);
		log.setState(DataConstants.Y);
		IUser user = FworkHelper.getUser();
		if( user != null )
		{
			log.setUserId(user.getId());
			log.setUserName(user.getName());
		}
		if( ex != null )
		{

			log.setState(DataConstants.N);
			log.setExceptionMess(ex.getMessage());
			log.setExceptionStack(StackTraceHelper.getSignificantStackTrace(ex));
		}
		StringBuffer requestParam = new StringBuffer();

		Map m = WebUtils.getParametersStartingWith(HttpServletHelper.getRequest(),null);
		String body = ThreadLocalHelper.get(AccessLogFilter.REQUEST_BODY_PARAM);
		if( StringUtils.isNotEmpty(body ) )
		{
			requestParam.append("{");
			requestParam.append("\"query\":").append(JSON.toJSONString(m));
			requestParam.append(",");

			requestParam.append("\"body\":").append(body);
			requestParam.append("}");
		}
		else
		{
			requestParam.append(JSON.toJSONString(m));
		}
		log.setRequestParam(requestParam.toString());
	}

	private AccessLog create()
	{
		AccessLog log = new AccessLog();
		HttpServletRequest request = HttpServletHelper.getRequest();
		log.setId(createId());
		log.setUri(request.getRequestURI());
		log.setRequestUrl(request.getRequestURL().toString());
		String traceId = TraceContext.traceId();
		if( StringUtils.isNotEmpty(traceId) && !"[Ignored Trace]".equalsIgnoreCase(traceId) )
		{
			log.setTraceId(traceId);
		}

		log.setStartTime(new Date());
		log.setClientIp( HttpServletHelper.getClientIp() );
		Map<String,String> headerMap = accessHeaders();
		log.setHeaders(JsonHelper.toJson(headerMap));
		log.setAppCode(AppContextHelper.getAppCode());

		return log;
	}

	private Map<String,String> accessHeaders()
	{
		Map<String,String> headerMap = new HashMap();
		List<String> accessHeaders = accessLogProperties.getHeaders();
		HttpServletRequest request = HttpServletHelper.getRequest();
		if( accessHeaders != null && !accessHeaders.isEmpty() ) {

			for( String headerName : accessHeaders )
			{
				String val = request.getHeader(headerName);
				if( StringUtils.isNotEmpty(val) )
				{
					headerMap.put(headerName,val);
				}
			}
		}
		return headerMap;
	}

	private String createId()
	{
		return DateFormatUtils.format(new Date(),"yyyyMM") + UUID.randomUUID().toString().replaceAll("-","");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


	private class HttpAccessContext implements AccessContext
	{

		@Override
		public String getAccessId() {
			String accessId = HttpServletHelper.getRequest().getHeader(FrameCommonConstants.ACCESS_ID);
			if( StringUtils.isEmpty(accessId) )
			{
				accessId = (String)HttpServletHelper.getRequest().getAttribute(FrameCommonConstants.ACCESS_ID);
			}

			return accessId;
		}
	}

}
