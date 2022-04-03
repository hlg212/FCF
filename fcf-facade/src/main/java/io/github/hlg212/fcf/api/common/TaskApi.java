package  io.github.hlg212.fcf.api.common;

import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.basic.TaskInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(contextId = Constants.ApiContextId.TaskApi,name=Constants.ApiName.TaskApi, url=Constants.AppFeignUrl.TaskApi)
@RequestMapping(Constants.ApiMapping.TaskApi)
@ConditionalOnExpression("false")
public interface TaskApi{
	@RequestMapping(value="/execute/{beanName}",method=RequestMethod.POST)
	public String execute(@PathVariable("beanName")String beanName, @RequestParam(name="param",required = false) String param);

	@RequestMapping(value="/getTasks",method=RequestMethod.GET)
	public List<TaskInfo> getTasks();
	
}
