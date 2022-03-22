package  io.github.hlg212.fcf.web.controller;

import  io.github.hlg212.fcf.api.AutomateApi;
import  io.github.hlg212.fcf.properties.AutomateProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author huangligui
 * @date 2019年1月11日
 */
@RestController("frame.automateController")
@Api(value="框架自动化控制器",tags={"框架自动化控制器"})
@EnableConfigurationProperties(AutomateProperties.class)
public class AutomateController implements AutomateApi{

	@Autowired
	private AutomateProperties automateProperties;

	@Override
	@ApiOperation(value="服务自动化配置",notes="根据该配置决定是否进行自动注册注册")
	public AutomateProperties getAutomateConfig() {
		return automateProperties;
	}
}
