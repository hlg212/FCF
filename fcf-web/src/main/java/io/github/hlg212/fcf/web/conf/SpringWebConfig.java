/** 
 * Project frame-web 
 * File Name:SpringWebConfig.java
 * Package Name: io.github.hlg212.fcf.web.conf
 * Date:2018年3月27日 下午5:37:37 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.web.conf;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import  io.github.hlg212.fcf.web.annotation.MvcConditional;
import  io.github.hlg212.fcf.web.filter.Constants;
import  io.github.hlg212.fcf.web.filter.FworkContextFilter;
import  io.github.hlg212.fcf.web.filter.ThreadLocalFilter;
import  io.github.hlg212.fcf.web.filter.TokenFilter;
import  io.github.hlg212.fcf.web.resolver.RequestParamOrBodyArgumentResolver;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

/** 
 * ClassName: SpringWebConfig
 * Function: TODO ADD FUNCTION. 
 * Reason: TODO ADD REASON(可选).
 * date: 2018年3月27日 下午5:37:37
 *
 * @author  huangligui
 */
@Configuration( io.github.hlg212.fcf.Constants.FRAME_BEAN_PREFIX + "SpringWebConfig")
@MvcConditional
public class SpringWebConfig implements WebMvcConfigurer {

	@Autowired
	private RequestParamOrBodyArgumentResolver requestParamOrBodyArgumentResolver;

	@Value("${hlg.mvc.cors.enable:false}")
	private Boolean corsAllowed;

	@Value("${hlg.mvc.isnullfield.display:false}")
	private Boolean isNullFieldDisplay;

	@Bean
	public MappingJackson2HttpMessageConverter createMappingJackson2HttpMessageConverter(){
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

		ObjectMapper objectMapper = new ObjectMapper();
		if(!isNullFieldDisplay){
			objectMapper.setSerializationInclusion(Include.NON_NULL);
		}
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
		jackson2HttpMessageConverter.setObjectMapper(objectMapper);

		return jackson2HttpMessageConverter;
	}


	@Bean
	public HystrixCommandAspect hystrixAspect() {
		return new HystrixCommandAspect();
	}
	
	@Bean
    public ServletRegistrationBean<?> getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean<?> registrationBean = new ServletRegistrationBean<>(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(requestParamOrBodyArgumentResolver);
	}

	@Bean
	public FilterRegistrationBean threadLocalFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new ThreadLocalFilter());
		registration.addUrlPatterns("/*");
		registration.setOrder(Constants.Order.ThreadLocalFilter);
		return registration;
	}
	@Bean
	public FworkContextFilter fworkContextFilter()
	{
		return new FworkContextFilter();
	}

	@Bean
	public FilterRegistrationBean fworkContextFilterReg(FworkContextFilter fworkContextFilter) {
		FilterRegistrationBean registration = new FilterRegistrationBean(fworkContextFilter);
		registration.addUrlPatterns("/*");
		registration.setOrder(Constants.Order.FworkContextFilter);
		return registration;
	}

	@Bean
	public TokenFilter tokenFilter()
	{
		return new TokenFilter();
	}

	@Bean
	public FilterRegistrationBean tokenFilterReg(TokenFilter tokenFilter) {
		FilterRegistrationBean registration = new FilterRegistrationBean(tokenFilter);
		registration.addUrlPatterns("/*");
		registration.setOrder(Constants.Order.TokenFilter);
		return registration;
	}

	@Bean
	public RequestParamOrBodyArgumentResolver requestParamOrBodyArgumentResolver()
	{
		return new RequestParamOrBodyArgumentResolver();
	}
	@Override
	public void addCorsMappings(CorsRegistry registry)
	{
		if( corsAllowed != null && corsAllowed)
		{
			registry.addMapping("/**")
					.allowedOrigins("*")
					.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
					.maxAge(3600)
					.allowCredentials(true);
		}

	}


	@Bean
	public WebMvcRegistrations feignWebRegistrations() {

		return new WebMvcRegistrations() {

			@Override
			public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {

				return new CusRequestMappingHandlerMapping();
			}
		};
	}

	private class CusRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
		@Override
		protected boolean isHandler(Class<?> beanType) {
			return super.isHandler(beanType) && !beanType.isInterface();
		}

		//registerHandlerMethod

        @Override
        protected Comparator<RequestMappingInfo> getMappingComparator(HttpServletRequest request) {

            return new EqualsComparator( super.getMappingComparator(request) );
        }

        @Override
        protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
            super.registerHandlerMethod(handler, method, mapping);
        }

        @Override
        public void registerMapping(RequestMappingInfo mapping, Object handler, Method method) {
            super.registerMapping(mapping, handler, method);
        }

        private class EqualsComparator implements Comparator<RequestMappingInfo>
        {
            private Comparator<RequestMappingInfo> comparator;

            private EqualsComparator(Comparator<RequestMappingInfo> comparator)
            {
                this.comparator = comparator;
            }
            @Override
            public int compare(RequestMappingInfo o1, RequestMappingInfo o2) {
               int comp = comparator.compare(o1,o2);
                if( comp == 0)
                    return 1;
                return comp;
            }
        }
    }

}
