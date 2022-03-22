package  io.github.hlg212.fcf.constants;

/**
 * 框架数据统一常量<br/>
 *
 * @author huangligui
 * @date  2022年3月21日 下午2:41:09
 */
public class FrameCommonConstants {


	/**
	 *  保存在request attr 中的异常
	 */
	public final static String REQUEST_EXCEPTION_KEY = "REQUEST_EXCEPTION_KEY";

	/**
	 * 框架内部feignCLient请求 header请求头标识变量
	 */
	public final static String FEIGN_REQUEST_KEY = "FEIGN_REQUEST_FLAG";

	/**
	 *  统一跟踪ID
	 */
	public final static String ACCESS_ID = "FCF_ACCESS_ID";

	/**
	 * 前端客户端IP
	 */
	public final static String CLIENT_IP = "FCF_CLIENT_IP";

	
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
