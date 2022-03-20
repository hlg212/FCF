package com.hlg.fcf.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {


    @Bean
    public  PasswordEncoder passwordEncoder(){
        //return new MessageDigestPasswordEncoder("MD5");
        return new MessageDigestPasswordEncoder("MD5");
        // return PasswordUtils.bcrypt;
    }
}
