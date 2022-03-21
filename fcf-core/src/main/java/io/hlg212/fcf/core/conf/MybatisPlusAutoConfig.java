/** 
 * Project Name:frame-core 
 * File Name:MybatisConfig.java 
 * Package Name:io.hlg212.fcf.core.conf 
 * Date:2018年8月20日 下午1:06:25 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package io.hlg212.fcf.core.conf;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import io.hlg212.fcf.core.dao.impl.*;
import io.hlg212.fcf.core.mybatis.plus.DaoMetaObjectHandler;
import io.hlg212.fcf.core.mybatis.plus.DaoSqlInjector;
import io.hlg212.fcf.core.mybatis.plus.MybatisConfigurationEx;
import io.hlg212.fcf.core.properties.AutoFillFieldProperties;
import io.hlg212.fcf.core.properties.AutoFillProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;


/**
 * mybatis-plus增强，自动填充等；
 *
 * @author huangligui
 * @date 2019年7月7日
 */
@Slf4j
@ConditionalOnSingleCandidate(DataSource.class)
@AutoConfigureBefore({MybatisPlusAutoConfiguration.class})
@EnableConfigurationProperties({MybatisPlusProperties.class,AutoFillProperties.class,AutoFillFieldProperties.class})
public class MybatisPlusAutoConfig {


    public MybatisPlusAutoConfig( MybatisPlusProperties properties)
    {
        properties.setConfiguration(new MybatisConfigurationEx());
    }

    @Bean
    public DaoSqlInjector daoSqlInjector()
    {
        return new DaoSqlInjector();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLimit(AbsBaseDao.MAX_PAGE_SIZE_DEFAULT);
        return paginationInterceptor;
    }

    @Bean
    public CreateUserIdFillHandler createUserIdFillHandler() {
        return new CreateUserIdFillHandler();
    }

    @Bean
    public UpdateUserIdFillHandler updateUserIdFillHandler() {
        return new UpdateUserIdFillHandler();
    }



    @Bean
    public CreateUserFillHandler createUserFillHandler() {
        return new CreateUserFillHandler();
    }

    @Bean
    public CreateTimeFillHandler createTimeFillHandler() {
        return new CreateTimeFillHandler();
    }

    @Bean
    public UpdateUserFillHandler updateUserFillHandler() {
        return new UpdateUserFillHandler();
    }
    @Bean
    public UpdateTimeFillHandler updateTimeFillHandler() {
        return new UpdateTimeFillHandler();
    }

    @Bean
    public DaoMetaObjectHandler daoMetaObjectHandler() {
        return new DaoMetaObjectHandler();
    }



}
