
package com.hlg.fcf.model.mq;

import com.hlg.fcf.ISerializable;

public interface IExchange extends ISerializable {

	public Boolean getAutoDelete();

	public int getConsumers();

	public Boolean getDurable();

	public String getName();
}
