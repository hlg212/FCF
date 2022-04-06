package  io.github.hlg212.fcf.core.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;

import javax.sql.DataSource;

/**
 * mybatis自动包目录扫描
 *
 * @author  huangligui
 * @create: 2019-03-01 13:23
 */
@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnMissingBean(MapperScannerRegistrar.class)
@AutoConfigureBefore({MybatisAutoConfiguration.class})
@MapperScan(value = "${fcf.package.dao}")
public  class MapperScannerConfig{



}