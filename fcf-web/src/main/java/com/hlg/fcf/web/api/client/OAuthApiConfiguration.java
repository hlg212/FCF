package com.hlg.fcf.web.api.client;

import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class OAuthApiConfiguration {

    @Value("${security.oauth2.client.clientId:}")
    private String clientId;
    @Value("${security.oauth2.client.clientSecret:}")
    private String clientSecret;

    @Bean
    public RequestInterceptor authInterceptor()
    {
        return new BasicAuthRequestInterceptor(clientId,clientSecret);
    }
}
