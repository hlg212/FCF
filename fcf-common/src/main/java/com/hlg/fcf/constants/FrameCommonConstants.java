/**
 * 
 */
package com.hlg.fcf.constants;

/**
 * 框架内部一些常用常量
 *
 * @author changwei
 * @date 2018年9月18日
 */
public class FrameCommonConstants {


	/**
	 *  保存在request attr 中的异常
	 * @see ExceptionHandlerAdvice
	 */
	public final static String REQUEST_EXCEPTION_KEY = "REQUEST_EXCEPTION_KEY";

	/**
	 * 框架内部feignCLient请求 header请求头标识变量
	 */
	public final static String HTCF_FEIGN_REQUEST_KEY = "HTCF_FEIGN_REQUEST_FLAG";

	/**
	 *  统一跟踪ID
	 */
	public final static String HTCF_ACCESS_ID = "HTCF_ACCESS_ID";

	/**
	 * 前端客户端IP
	 */
	public final static String HTCF_CLIENT_IP = "HTCF_CLIENT_IP";

	
	/**
	 * 动态数据源key
	 */
	public final static String DYNAMIC_DATA_SOURCE_KEY = "DYNAMIC_DATA_SOURCE_KEY";

	
	/**
	 * cache id生成器
	 */
	public final static String PK_ID_GENERATOR = "pkIdGenerator";
	
	/**
	 * 当前所请求的cache method对象
	 */
	public final static String REQUEST_CACHE_METHOD = "REQUEST_METHOD_KEY";
	
	/**
	 * cacheManager bean后缀
	 */
	public final static String CACHE_MANAGER_BEAN_SUFFIX = "CacheManager";
	

}
