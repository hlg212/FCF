package com.hlg.fcf.util;

import com.hlg.fcf.context.AccessContext;

/** 
 * 返回访问ID
 * 
 * ClassName: AccessContextHelper
 * Function: TODO ADD FUNCTION. 
 * Reason: 框架工具
 * date: 2019年5月8日 上午8:50:16
 * 
 * @author huangligui 
 */
public class AccessContextHelper {
	private static final String ACCESS_CONTEXT = "ACCESS_CONTEXT";

	public static String  getAccessId()
	{
		AccessContext context = getContext();
		if( context != null ){
			return context.getAccessId();
		}
		return null;
	}

	public static AccessContext getContext()
	{
		return (AccessContext)ThreadLocalHelper.get(ACCESS_CONTEXT);
	}

	public static void setContext(AccessContext context)
	{
		ThreadLocalHelper.set(ACCESS_CONTEXT, context);
	}
}
