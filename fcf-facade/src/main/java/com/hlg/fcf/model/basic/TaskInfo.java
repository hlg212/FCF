
package com.hlg.fcf.model.basic;

import com.hlg.fcf.ISerializable;
import com.hlg.fcf.model.Model;
import lombok.Data;

@Data
public class TaskInfo implements ISerializable {

	private String name;
	private String beanName;
	private String cron;
	private String param;
	private boolean autoStart;
}
