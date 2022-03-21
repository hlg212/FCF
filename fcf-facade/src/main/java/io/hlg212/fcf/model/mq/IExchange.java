
package io.hlg212.fcf.model.mq;

import io.hlg212.fcf.ISerializable;

public interface IExchange extends ISerializable {

	public Boolean getAutoDelete();

	public int getConsumers();

	public Boolean getDurable();

	public String getName();
}
