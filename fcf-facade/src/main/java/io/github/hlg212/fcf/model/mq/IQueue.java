
package  io.github.hlg212.fcf.model.mq;

import  io.github.hlg212.fcf.ISerializable;

public interface IQueue extends ISerializable {
	public Boolean getAutoDelete();

	public int getConsumers();

	public Boolean getDurable();

	public String getName();

	public int getMessagesReady();

	public Boolean getExclusive();
}
