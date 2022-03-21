package io.hlg212.fcf.service.interceptor;

import io.hlg212.fcf.service.FrameService;
import io.hlg212.fcf.service.impl.Constants;
import io.hlg212.fcf.service.impl.ServiceWapper;
import io.hlg212.fcf.util.SpringHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * service代理包装注入dao
 *
 * @author huangligui
 * @date 2020年8月18日
 */
@Component
@Slf4j
public class ServiceWapperBeanPostProcessor implements BeanPostProcessor,ApplicationContextAware {


    private ApplicationContext applicationContext;
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if( bean instanceof FrameService )
        {
            if( beanName.endsWith(Constants.SERVICEPROXY_SUFFIX) )
            {
                if( bean instanceof  ServiceWapper )
                {
                    ServiceWapper sw = (ServiceWapper)bean;
                    Class c = sw.getServiceClass();
                    Map map = applicationContext.getBeansOfType(c);
                    for(Object o : map.values() )
                    {
                        if( o != bean && !(o instanceof ServiceWapper) )
                        {
                            sw.setWorkService(o);
                            break;
                        }
                    }
                }
            }
        }

        return bean;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        SpringHelper.initApplicationContext(applicationContext);

    }

}
