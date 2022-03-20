package com.hlg.fcf.util;

import com.hlg.fcf.context.TransactionContext;
import com.hlg.fcf.model.log.ITransactionLog;

/** 
 * 返回当前事务日志对象
 * 
 * ClassName: TransactionLogContextHelper
 * Function: TODO ADD FUNCTION. 
 * Reason: 框架工具
 * date: 2019年5月8日 上午8:50:16
 * 
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
