package io.hlg212.fcf.core.mybatis.plus;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisMapperRegistry;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.type.JdbcType;

/**
 *  plus 集成增强
 *
 * ClassName: HtcfMybatisConfiguration
 * date: 2019年7月10日 上午8:50:16
 *
 * @author huangligui
 */

public class MybatisConfigurationEx extends MybatisConfiguration {
    protected final MybatisMapperRegistry mybatisMapperRegistry;

    public MybatisConfigurationEx() {
        super();
        this.setJdbcTypeForNull(JdbcType.NULL);
        this.mybatisMapperRegistry = new DaoMapperRegistry(this);
    }

    @Override
    public MapperRegistry getMapperRegistry() {
        return this.mybatisMapperRegistry;
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        this.mybatisMapperRegistry.addMapper(type);
    }

    @Override
    public void addMappers(String packageName, Class<?> superType) {
        this.mybatisMapperRegistry.addMappers(packageName, superType);
    }

    @Override
    public void addMappers(String packageName) {
        this.mybatisMapperRegistry.addMappers(packageName);
    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return this.mybatisMapperRegistry.getMapper(type, sqlSession);
    }
    @Override
    public boolean hasMapper(Class<?> type) {
        return this.mybatisMapperRegistry.hasMapper(type);
    }
}