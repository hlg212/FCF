package com.hlg.fcf.web.api.client;

import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class MqApiConfig {


    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public RequestInterceptor authInterceptor()
    {
        return new BasicAuthRequestInterceptor(username,password);
    }
}
