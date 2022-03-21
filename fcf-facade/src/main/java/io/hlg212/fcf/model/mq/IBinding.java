
package io.hlg212.fcf.model.mq;

import io.hlg212.fcf.ISerializable;

public interface IBinding extends ISerializable {

	public static String DESTTYPE_EXCHANGE = "exchange";

	public String getDestination();

	public String getDestinationType();

	public String getRoutingKey();

	public String getSource();
}
