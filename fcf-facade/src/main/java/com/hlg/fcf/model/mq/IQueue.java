
package com.hlg.fcf.model.mq;

import com.hlg.fcf.ISerializable;

public interface IQueue extends ISerializable {
	public Boolean getAutoDelete();

	public int getConsumers();

	public Boolean getDurable();

	public String getName();

	public int getMessagesReady();

	public Boolean getExclusive();
}
