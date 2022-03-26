package  io.github.hlg212.fcf.web.controller;

import  io.github.hlg212.fcf.web.annotation.MvcConditional;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description: 首页控制器
 * 如果直接登录 认证中心 ，默认跳转到一个 导航页面
 *
 * @author  huangligui
 * @create: 2018-10-26 10:30
 **/
@RestController
@MvcConditional
@Api(value="框架首页控制器",tags={"框架首页控制器"})
@ConditionalOnProperty(matchIfMissing = true,value = "enable",prefix = "fcf.index")
@RefreshScope
public class IndexController {

    @Value("${fcf.index.indexPath:redirect:/views/index.html}")
    private String indexPath;

    @RequestMapping(value = "/",method=RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView(indexPath);
        return modelAndView;
    }

    @RequestMapping(value="/index",method=RequestMethod.GET )
    public ModelAndView indexPage() {
        ModelAndView modelAndView = new ModelAndView(indexPath);
        return modelAndView;
    }


}
