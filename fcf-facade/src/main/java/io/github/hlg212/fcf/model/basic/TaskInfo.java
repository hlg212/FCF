
package  io.github.hlg212.fcf.model.basic;

import  io.github.hlg212.fcf.ISerializable;
import  io.github.hlg212.fcf.model.Model;
import lombok.Data;

@Data
public class TaskInfo implements ISerializable {

	private String name;
	private String beanName;
	private String cron;
	private String param;
	private boolean autoStart;
}
