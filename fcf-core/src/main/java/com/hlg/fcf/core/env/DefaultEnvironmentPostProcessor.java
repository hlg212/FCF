package com.hlg.fcf.core.env;

import com.hlg.fcf.env.DefaultPackagePropertySource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
public class DefaultEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        environment.getPropertySources().addFirst(new DefaultPackagePropertySource("DefaultPackage"));
    }

}
