package  io.github.hlg212.fcf.web.conf;

import  io.github.hlg212.fcf.annotation.AccessLogConditional;
import  io.github.hlg212.fcf.properties.AccessLogProperties;
import  io.github.hlg212.fcf.web.annotation.MvcConditional;
import  io.github.hlg212.fcf.web.filter.AccessLogFilter;
import  io.github.hlg212.fcf.web.filter.Constants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration( io.github.hlg212.fcf.Constants.FRAME_BEAN_PREFIX + "AccessLogConfig")
@AccessLogConditional
@MvcConditional
@EnableConfigurationProperties(AccessLogProperties.class)
public class AccessLogConfig {

    @Bean
    public AccessLogFilter accessLogFilter()
    {
        return new AccessLogFilter();
    }

    @Bean
    public FilterRegistrationBean accessLogFilterReg(AccessLogFilter accessLogFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(accessLogFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(Constants.Order.AccessLogFilter);
        return registration;
    }


}
