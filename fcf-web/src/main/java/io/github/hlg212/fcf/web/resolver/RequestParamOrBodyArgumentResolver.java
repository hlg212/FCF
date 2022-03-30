package  io.github.hlg212.fcf.web.resolver;

import com.alibaba.fastjson.JSON;
import  io.github.hlg212.fcf.annotation.RequestParamOrBody;
import  io.github.hlg212.fcf.util.StreamHelper;
import  io.github.hlg212.fcf.util.ThreadLocalHelper;
import  io.github.hlg212.fcf.web.filter.AccessLogFilter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * 自定义@RequestParamOrBody 注解解析器
 *
 * @author huangligui
 * @date 2018年10月24日
 */
@Slf4j
@Data
public class RequestParamOrBodyArgumentResolver implements HandlerMethodArgumentResolver {

    private ServletModelAttributeMethodProcessor servletModelAttributeMethodProcessor = new ServletModelAttributeMethodProcessor(true);

    private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;

    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    //EmptyHttpInputMessage emptyHttpInputMessage = new EmptyHttpInputMessage();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(RequestParamOrBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //servletModelAttributeMethodProcessor 类型无法确定，所以先使用一个空数据进行转换；
        Object empty =  mappingJackson2HttpMessageConverter.read(parameter.getNestedGenericParameterType(),parameter.getContainingClass(),new EmptyHttpInputMessage());

        if(empty != null) {
            String name = ModelFactory.getNameForParameter(parameter);
            mavContainer.getModel().put(name, empty);
        }
        Object paramValue =  servletModelAttributeMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        Object bodyValue = requestResponseBodyMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        this.mergeObject(paramValue, bodyValue);
        Object val = bodyValue == null ? paramValue : bodyValue;
        ThreadLocalHelper.set(AccessLogFilter.REQUEST_BODY_PARAM,JSON.toJSONString(val));
        return val;
    }
    
	private <M> void mergeObject(M target, M destination) throws Exception {
		if(destination == null) {
			return ;
		}
		if(target == null) {
			target = destination;
			return;
		}
        BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
       
        // Iterate over all the attributes
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

            // Only copy writable attributes
            if (descriptor.getWriteMethod() != null) {
                Object originalValue = descriptor.getReadMethod()
                        .invoke(target);

                // Only copy values values where the destination values is null
                if (originalValue == null ||
                		((originalValue instanceof Collection) && ((Collection<?>)originalValue).size() == 0) || 
                		((originalValue instanceof Map) && ((Map<?, ?>)originalValue).size() == 0)) {
                    Object defaultValue = descriptor.getReadMethod().invoke(
                            destination);
                    
                    if(defaultValue == null) {
                    	continue;
                    }
                    if(defaultValue instanceof Collection) {
                    	Collection<?> c = (Collection<?>) defaultValue;
                    	if(c.size() == 0) {
                    		continue;
                    	}
                    }
                    if(defaultValue instanceof Map) {
                    	Map<?, ?> m = (Map<?, ?>) defaultValue;
                    	if(m.size() == 0) {
                    		continue;
                    	}
                    }
                    descriptor.getWriteMethod().invoke(target, defaultValue);
                }
            }
        }
    }

}
