package com.hlg.fcf.api.common;

import com.hlg.fcf.api.Constants;
import com.hlg.fcf.model.basic.TaskInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 
 * @author wuwei
 * @date 2019年2月22日
 */
@FeignClient(contextId = Constants.ApiContextId.TaskApi,name=Constants.APP_APIGATEWAY_TASK, url=Constants.AppFeignUrl.APP_TASK)
@RequestMapping(Constants.ApiMapping.TaskApi)
@ConditionalOnExpression("false")
public interface TaskApi{
	@RequestMapping(value="/execute/{beanName}",method=RequestMethod.POST)
	public String execute(@PathVariable("beanName")String beanName, @RequestParam(name="param",required = false) String param);

	@RequestMapping(value="/getTasks",method=RequestMethod.GET)
	public List<TaskInfo> getTasks();
	
}
