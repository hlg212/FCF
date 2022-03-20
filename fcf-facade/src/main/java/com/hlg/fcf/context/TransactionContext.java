package com.hlg.fcf.context;

import com.hlg.fcf.model.log.ITransactionLog;


public interface TransactionContext {

	public ITransactionLog getLog();
	
}
