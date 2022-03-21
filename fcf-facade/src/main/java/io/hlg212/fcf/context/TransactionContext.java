package io.hlg212.fcf.context;

import io.hlg212.fcf.model.log.ITransactionLog;


public interface TransactionContext {

	public ITransactionLog getLog();
	
}
