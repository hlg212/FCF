package io.hlg212.fcf.util;

import io.hlg212.fcf.exception.BaseException;
import io.hlg212.fcf.model.ActionResultBuilder;

/**
 *  框架内部异常抛出工具类，主要用于抛出业务异常和服务内部异常。两者异常处理级别:业务异常<错误异常
 *
 * @author: huangligui
 * @create: 2019-02-25 16:01
 */
public class ExceptionHelper {

	/**
	 * 业务异常code
	 */
	public static int BUSINESS_EXCEPTION_CODE = ActionResultBuilder.ActionResultStatus.BUSINESS_ERROR.getCode();
	
	/**
	 * 服务器内部异常code
	 */
	public static int SERVICE_EXCEPTION_CODE = ActionResultBuilder.ActionResultStatus.INTERNAL_SERVER_ERROR.getCode();
	
	/**
	 * 抛出服务器业务异常，一般用于传入参数校验错误等
	 * @param message 异常信息
	 */
	public static void throwBusinessException(String message) {
		throw new BaseException(BUSINESS_EXCEPTION_CODE, message);
	}
	
	/**
	 * 抛出服务器业务异常，一般用于传入参数校验错误等
	 * @param code 异常code
	 * @param message 异常信息
	 * @param e 异常对象
	 */
	public static void throwBusinessException(int code, String message, Exception e) {
		throw new BaseException(e, code, message);
	}
	
	/**
	 * 抛出服务器内部错误异常，一般在方法体catch内使用
	 * @param message 异常信息
	 */
	public static void throwServerException(String message) {
		throw new BaseException(SERVICE_EXCEPTION_CODE, message);
	}
	
	/**
	 * 抛出服务器内部错误异常，一般在方法体catch内使用
	 * @param message 异常信息
	 * @param e 异常对象
	 */
	public static void throwServerException(String message, Exception e) {
		throw new BaseException(e, SERVICE_EXCEPTION_CODE, message);
	}
	
	/**
	 * 抛出服务器内部错误异常，一般在方法体catch内使用
	 * @param e 异常对象
	 */
	public static void throwServerException(Exception e) {
		throw new BaseException(e, SERVICE_EXCEPTION_CODE, e.getMessage());
	}
	
	/**
	 * 抛出自定义code异常，一般在方法体catch内使用
	 * @param code 异常code
	 * @param message 异常信息
	 */
	public static void throwException(int code, String message) {
		throw new BaseException(code, message);
	}

	/**
	 * 抛出自定义code异常，一般在方法体catch内使用
	 * @param code 异常code
	 * @param message 异常信息
	 */
	public static void throwException(int code, String message,Object data) {
		BaseException error = new BaseException(code, message);
		error.setData(data);
		throw  error;

	}
	
	/**
	 * 抛出自定义code异常，一般在方法体catch内使用
	 * @param code 异常code
	 * @param message 异常信息
	 * @param e 异常对象
	 */
	public static void throwException(int code, String message, Exception e) {
		throw new BaseException(e, code, message);
	}
}
