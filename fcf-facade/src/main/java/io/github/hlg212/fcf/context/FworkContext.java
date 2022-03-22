package  io.github.hlg212.fcf.context;

import  io.github.hlg212.fcf.model.basic.IUser;


/** 
 * ClassName: FworkContext
 * Function: 框架环境对象
 * Reason: @see FworkHelper
 * 一般有 web,dubbo 两种环境
 * date: 2017年8月23日 上午8:56:29
 * 
 * @author huangligui 
 */
public interface FworkContext {

	public final static String FWORKCONTEXT_ATTRIBUTES = "FWORKCONTEXT_ATTRIBUTES";
	
	public IUser getUser();
	
	public String getUid();
	
	public Object get(String key);
	
	public void put(String key,Object o);
	
	public static enum Attributes
	{
		
		USER_ID("FWORK_USER_ID"),
		REQ_APP_CODE("FWORK_REQ_APP_CODE");
		
		private String value = null;
		
		private Attributes(String value)  {
			// TODO Auto-generated constructor stub
			this.value = value;
		}
		
		/**
		 *   the value
		 */
		public String getValue() {
			return value;
		}
		
	}
	
	
}
