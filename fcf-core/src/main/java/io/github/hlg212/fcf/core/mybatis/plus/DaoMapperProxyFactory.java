package  io.github.hlg212.fcf.core.mybatis.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.override.MybatisMapperProxy;
import com.baomidou.mybatisplus.core.override.MybatisMapperProxyFactory;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import  io.github.hlg212.fcf.core.dao.impl.BaseDaoImpl;
import  io.github.hlg212.fcf.dao.BaseDao;
import io.github.hlg212.fcf.util.SpringHelper;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 *  plus 集成增强
 *
 * ClassName: DaoMapperProxyFactory
 * date: 2019年7月10日 上午8:50:16
 *
 * @author huangligui
 */
public class DaoMapperProxyFactory<T> extends MybatisMapperProxyFactory<T> {

    private final Configuration config;

    public DaoMapperProxyFactory(Class<T> mapperInterface, Configuration config) {
        super(mapperInterface);
        this.config = config;
    }


    @Override
    protected T newInstance(MybatisMapperProxy<T> mapperProxy) {

        if (GlobalConfigUtils.isSupperMapperChildren(config, getMapperInterface())) {
            return super.newInstance(mapperProxy);
        }

        return (T) Proxy.newProxyInstance(getMapperInterface().getClassLoader(), new Class[]{getMapperInterface(), BaseMapper.class}, mapperProxy);
    }

    @Override
    public T newInstance(SqlSession sqlSession) {

        T proxy = super.newInstance(sqlSession);

        Enhancer enhancer = new Enhancer();
        //设置要创建动态代理的类
        enhancer.setInterfaces(new Class[]{getMapperInterface(), BaseDao.class});
        enhancer.setSuperclass(BaseDaoImpl.class);
        enhancer.setCallback(new DaoEnhancer(proxy, getMapperInterface()));

        BaseDao baseDao = (BaseDao) enhancer.create();
        ApplicationContext context = SpringHelper.getApplicationContext();
        if( context != null ) {
            AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
            factory.autowireBean(baseDao);
        }
        // return (T)new BaseDaoImpl();
        return (T) baseDao;
    }


    class DaoEnhancer implements MethodInterceptor {

        private T mapperProxy;
        private Class mapperInterface;

        public DaoEnhancer(T mapperProxy, Class mapperInterface) {
            this.mapperProxy = mapperProxy;
            this.mapperInterface = mapperInterface;
        }

        private static final String METHOD_GETMAPPER = "getMapper";

        @Override
        public Object intercept(Object obj, Method method, Object[] args,
                                MethodProxy proxy) throws Throwable {
            if (method.getName().equals(METHOD_GETMAPPER)) {
                return getMapper();
            }
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }
            if (method.getDeclaringClass().isAssignableFrom(BaseDaoImpl.class)) {
                return proxy.invokeSuper(obj, args);
            }
            return proxy.invoke(mapperProxy, args);
        }

        private Object getMapper() {
            return mapperProxy;
        }

    }
}
