package io.hlg212.fcf.web.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.DefaultCorsProcessor;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;

/**
 * ClassName: WebFluxConfig
 * Reason: 重写flux的RestController注解扫描判断，防止feign接口也被扫描
 * date: 2020年3月13日 下午2:13:58
 *
 * @author huangligui
 */
@Configuration(io.hlg212.fcf.Constants.FRAME_BEAN_PREFIX + "WebFluxConfig")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnClass(WebFluxConfigurer.class)
public class WebFluxConfig implements WebFluxRegistrations{

    private ReactiveRequestMappingHandlerMapping reactiveRequestMappingHandlerMapping = null;

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {

        return reactiveRequestMappingHandlerMapping();
    }

    //@Bean
    public ReactiveRequestMappingHandlerMapping reactiveRequestMappingHandlerMapping()
    {
        if(reactiveRequestMappingHandlerMapping == null )
        {
            reactiveRequestMappingHandlerMapping =  new ReactiveRequestMappingHandlerMapping();
             reactiveRequestMappingHandlerMapping.setCorsProcessor( new EmptyDefaultCorsProcessor() );

        }
        return reactiveRequestMappingHandlerMapping;
    }

    class ReactiveRequestMappingHandlerMapping extends RequestMappingHandlerMapping{
        @Override
        protected boolean isHandler(Class<?> beanType) {
            return super.isHandler(beanType) && !beanType.isInterface();
        }

    }
    class EmptyDefaultCorsProcessor extends DefaultCorsProcessor
    {
        @Override
        public boolean process(CorsConfiguration config, ServerWebExchange exchange) {
            return true;
        }
    }
}
