package com.hlg.fcf.web.advice;

import com.hlg.fcf.constants.FrameCommonConstants;
import com.hlg.fcf.exception.BaseException;
import com.hlg.fcf.model.ActionResult;
import com.hlg.fcf.model.ActionResultBuilder;
import com.hlg.fcf.util.AccessContextHelper;
import com.hlg.fcf.util.AppContextHelper;
import com.hlg.fcf.util.ExceptionHelper;
import com.hlg.fcf.web.annotation.MvcConditional;
import com.hlg.fcf.web.util.HttpServletHelper;
import feign.codec.DecodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

/**
 * ClassName: ExceptionHandlerAdvice 
 * Function: 异常处理
 * Reason:	封装应用返回的异常，将应用的异常转换成对用户友好的消息结果
 * 
 * date: 2017年8月22日 下午7:24:04
 * 
 * @author huangligui
 */
@Lazy
@Slf4j
@ControllerAdvice
@MvcConditional
public class ExceptionHandlerAdvice {

	/**
	 * 捕捉非法参数异常
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { MissingServletRequestParameterException.class, BindException.class })
	public ResponseEntity<ActionResult<String>> handleParameterException(Exception e) {
		saveExToRequest(e);
		errorLog(e);
		ActionResultBuilder<String> result = ActionResultBuilder.businessError();
		result.withMsg(e.getMessage());
		result.withData(getExceptionData(e));
		if(e instanceof BindException) {
			BindException be = (BindException) e;
			BindingResult bindResult = be.getBindingResult();
			StringBuffer sb = new StringBuffer();
			bindResult.getAllErrors().forEach(t -> sb.append(t.getDefaultMessage()).append(" && "));
			if(sb.length() > 4) {
				sb.delete(sb.length()-4, sb.length());
			}
			result.withMsg(sb.toString());
		}
		return ResponseEntity.ok(result.build());
	}

	@ExceptionHandler(value = DecodeException.class)
	public ResponseEntity<ActionResult<String>> handleFeignException(DecodeException e) {
		return handleException((Exception)e.getCause());
	}


	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ActionResult<String>> handleException(Exception e) {
		saveExToRequest(e);

		errorLog(e);
		ActionResultBuilder<String> result = ActionResultBuilder.serverError();
		result.withMsg(e.getMessage());
		if(e instanceof BaseException) {
			BaseException baseException = (BaseException) e;
			result.withCode(String.valueOf(baseException.getCode()));
		}
		if( isError(e) )
		{
			result.withMsg(getErrorMsg(e));
			result.withData(this.getExceptionData(e));
		}

		return ResponseEntity.ok(result.build());
	}

//	private String encodeError(Exception e)
//	{
//		StringBuffer code =  new StringBuffer();
//		code.append(AppContextHelper.getAppCode()).append("-");
//		String id = AccessContextHelper.getAccessId();
//		if( id != null )
//		{
//			code.append( String.valueOf( Long.toString(id.hashCode() & 0xffffffffl ,36) ) );
//		}
//		return code.toString();
//	}

	private String getErrorMsg(Exception e)
	{

		if( e instanceof  BaseException )
		{
			BaseException baseException = (BaseException) e;
			if( baseException.getData() != null && StringUtils.isNotEmpty(baseException.getData().toString()) )
			{
				return baseException.getMsg();
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append( e.getClass().getName() );
		if( StringUtils.isNotEmpty(e.getMessage()))
		{
			sb.append(":").append(e.getMessage());
		}
		return sb.toString();
	}
	private String getExceptionData(Exception e) {

		if( e instanceof  BaseException )
		{
			BaseException baseException = (BaseException) e;
			if( baseException.getData() != null && StringUtils.isNotEmpty(baseException.getData().toString()) )
			{
				return baseException.getData().toString();
			}
		}
		StringBuffer code =  new StringBuffer();
		code.append(AppContextHelper.getAppCode()).append("#");
		code.append(DateFormatUtils.ISO_DATE_FORMAT.format(new Date())).append("#");
		code.append(AccessContextHelper.getAccessId());
		return code.toString();
	}
//	private String getStackTrace(Exception e) {
//		if( !isError(e) )
//			return "";
//		StringBuffer sb = new StringBuffer();
//
//		if( e instanceof  BaseException )
//		{
//			BaseException baseException = (BaseException) e;
//			if( baseException.getData() != null && StringUtils.isNotEmpty(baseException.getData().toString()) )
//			{
//				sb.append( baseException.getData() ).append("\r\n");
//			}
//		}
//		sb.append(e).append("\r\n");
//		sb.append(StackTraceHelper.getSignificantStackTrace(e));
//
//		return sb.toString();
//	}

	private void saveExToRequest(Exception e)
	{
		if( isError(e)) {
			HttpServletHelper.getRequest().setAttribute(FrameCommonConstants.REQUEST_EXCEPTION_KEY, e);
		}
	}

	private void errorLog(Exception e)
	{
		MDC.put("ACCESS_ID",AccessContextHelper.getAccessId());
		if( isError(e) )
		{
			log.error(e.getMessage(), e);
		}
		MDC.remove("ACCESS_ID");
	}


	private boolean isError(Exception e)
	{
		if(e instanceof BaseException) {
			BaseException baseException = (BaseException) e;
			if( ExceptionHelper.BUSINESS_EXCEPTION_CODE == baseException.getCode() )
			{
				return false;
			}
		}
		return true;
	}

}
