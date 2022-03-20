package com.hlg.fcf.context;

/** 
 * ClassName: AppContext
 * Function: 应用系统环境对象
 * Reason: 通过 @see AppContextHelper 访问
 * 
 * @date: 2017年9月20日 下午4:35:22
 * 
 * @author huangligui 
 */
public interface AppContext {

	public String getAppId();
	
	public String getAppCode();
	
	public String getAppName();
	
}
