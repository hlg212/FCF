package com.hlg.fcf.web.conf;

import com.hlg.fcf.annotation.AccessLogConditional;
import com.hlg.fcf.properties.AccessLogProperties;
import com.hlg.fcf.web.annotation.MvcConditional;
import com.hlg.fcf.web.filter.AccessLogFilter;
import com.hlg.fcf.web.filter.Constants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(com.hlg.fcf.Constants.FRAME_BEAN_PREFIX + "AccessLogConfig")
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
