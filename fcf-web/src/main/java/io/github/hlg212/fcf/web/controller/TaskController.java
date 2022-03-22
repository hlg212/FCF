package  io.github.hlg212.fcf.web.controller;

import  io.github.hlg212.fcf.Task;
import  io.github.hlg212.fcf.annotation.TaskRegister;
import  io.github.hlg212.fcf.api.common.TaskApi;
import  io.github.hlg212.fcf.model.basic.TaskInfo;
import  io.github.hlg212.fcf.util.SpringHelper;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wuwei
 * @date 2019年2月22日
 */
@RestController("frame.taskController")
@Api(value="框架任务服务控制器",tags={"框架任务服务控制器"})
public class TaskController implements TaskApi {

	@Override
	public String execute(String beanName, String param) {
		Task task = (Task)SpringHelper.getBean(beanName);
		return task.executeTask(param);
	}


	@Override
	public List<TaskInfo> getTasks() {
		List<TaskInfo> taskInfos = new ArrayList<>();
		Map<String,Task> beans =  SpringHelper.getApplicationContext().getBeansOfType(Task.class);
		for(Map.Entry entry : beans.entrySet()){
			TaskInfo taskInfo = new TaskInfo();
			TaskRegister[] taskAnnotation = entry.getValue().getClass().getAnnotationsByType(TaskRegister.class);
			if( taskAnnotation != null && taskAnnotation.length > 0) {
				taskInfo.setName(taskAnnotation[0].name() + "");
				String beanName = taskAnnotation[0].beanName();
				if(StringUtils.isEmpty( taskAnnotation[0].beanName() ) )
				{
					beanName = entry.getKey().toString();
				}
				taskInfo.setBeanName(beanName);
				taskInfo.setCron(taskAnnotation[0].cron() + "");
				taskInfo.setParam(taskAnnotation[0].param() + "");
				taskInfo.setAutoStart(taskAnnotation[0].autoStart());
				taskInfos.add(taskInfo);
			}
		}

		return taskInfos;
	}
}
