package  io.github.hlg212.fcf.util;

import  io.github.hlg212.fcf.context.TransactionContext;
import  io.github.hlg212.fcf.model.log.ITransactionLog;

/** 
 * 返回当前事务日志对象
 *
 * @date  2019年5月8日 上午8:50:16
 * @author huangligui 
 */
public class TransactionContextHelper {
	private static final String TRANSACTION_CONTEXT = "TRANSACTION_CONTEXT";

	public static ITransactionLog getLog()
	{
		TransactionContext context = getContext();
		if( context != null ){
			return context.getLog();
		}
		return null;
	}

	public static TransactionContext getContext()
	{
		return (TransactionContext)ThreadLocalHelper.get(TRANSACTION_CONTEXT);
	}

	public static void setContext(TransactionContext context)
	{
		ThreadLocalHelper.set(TRANSACTION_CONTEXT, context);
	}
}
