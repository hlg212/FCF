package com.hlg.fcf.core.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;

import javax.sql.DataSource;


@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnMissingBean(MapperScannerRegistrar.class)
@AutoConfigureBefore({MybatisAutoConfiguration.class})
@MapperScan(value = "${hlg.package.dao}")
public  class MapperScannerConfig{



}