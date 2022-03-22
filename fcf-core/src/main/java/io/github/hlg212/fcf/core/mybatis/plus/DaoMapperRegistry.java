package  io.github.hlg212.fcf.core.mybatis.plus;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisMapperAnnotationBuilder;
import com.baomidou.mybatisplus.core.MybatisMapperRegistry;
import com.baomidou.mybatisplus.core.override.MybatisMapperProxyFactory;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.SqlSession;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *  plus 集成增强
 *
 * ClassName: DaoMapperRegistry
 * date: 2019年7月10日 上午8:50:16
 *
 * @author huangligui
 */
public class DaoMapperRegistry extends MybatisMapperRegistry {
    private final Map<Class<?>, MybatisMapperProxyFactory<?>> knownMappers = new HashMap();
    private final MybatisConfiguration config;

    public DaoMapperRegistry(MybatisConfiguration config) {
        super(config);
        this.config = config;
    }
    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MybatisMapperProxyFactory<T> mapperProxyFactory = (MybatisMapperProxyFactory)this.knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new BindingException("Type " + type + " is not known to the MybatisPlusMapperRegistry.");
        } else {
            try {
                return mapperProxyFactory.newInstance(sqlSession);
            } catch (Exception var5) {
                throw new BindingException("Error getting mapper instance. Cause: " + var5, var5);
            }
        }
    }
    @Override
    public <T> boolean hasMapper(Class<T> type) {
        return this.knownMappers.containsKey(type);
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (this.hasMapper(type)) {
                return;
            }

            boolean loadCompleted = false;

            try {
                this.knownMappers.put(type, new DaoMapperProxyFactory(type,config));
                DaoMybatisMapperAnnotationBuilder parser = new DaoMybatisMapperAnnotationBuilder(this.config, type);
                parser.parse();
                loadCompleted = true;
            } finally {
                if (!loadCompleted) {
                    this.knownMappers.remove(type);
                }

            }
        }

    }
    @Override
    public Collection<Class<?>> getMappers() {
        return Collections.unmodifiableCollection(this.knownMappers.keySet());
    }
}
