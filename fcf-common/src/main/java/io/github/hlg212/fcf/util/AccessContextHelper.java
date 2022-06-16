package  io.github.hlg212.fcf.util;

import  io.github.hlg212.fcf.context.AccessContext;

/** 
 * 返回访问ID
 *
 * @author huangligui
 * @date  2022年3月21日 下午2:41:09
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
	public static String  getTraceId()
	{
		AccessContext context = getContext();
		if( context != null ){
			return context.getTraceId();
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
