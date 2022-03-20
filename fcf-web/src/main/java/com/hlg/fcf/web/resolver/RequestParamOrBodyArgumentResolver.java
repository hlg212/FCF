package com.hlg.fcf.web.resolver;

import com.alibaba.fastjson.JSON;
import com.hlg.fcf.annotation.RequestParamOrBody;
import com.hlg.fcf.util.StreamHelper;
import com.hlg.fcf.util.ThreadLocalHelper;
import com.hlg.fcf.web.filter.AccessLogFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
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
 * @author changwei
 * @date 2018年10月24日
 */
@Slf4j
public class RequestParamOrBodyArgumentResolver implements HandlerMethodArgumentResolver {

    private ServletModelAttributeMethodProcessor attributeMethodProcessor = new ServletModelAttributeMethodProcessor(true);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(RequestParamOrBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Class<?> type = parameter.getParameterType();
        String requstParam = this.getRequestBodyValue(webRequest);
        log.debug("body value: {}",requstParam);
		Object bodyValue = null;
		try {
			if(StringUtils.isNotBlank(requstParam)) {
				bodyValue = JSON.parseObject(requstParam, type);
			}
		} catch (Exception e) {
		    log.warn("parseObject error：",e);
			bodyValue = null;
		}
		if(bodyValue != null) {
			String name = ModelFactory.getNameForParameter(parameter);
			mavContainer.getModel().put(name, bodyValue);
		}
        Object headValue = attributeMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        this.mergeObject(headValue, bodyValue);
        ThreadLocalHelper.set(AccessLogFilter.REQUEST_BODY_PARAM,JSON.toJSONString(headValue));
        return headValue;
    }

    private String getRequestBodyValue(NativeWebRequest webRequest) throws IOException {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        log.debug("characterEncoding:{}",   request.getCharacterEncoding());
        ServletInputStream in = request.getInputStream();
//        int len = 0;
//        byte[] b = new byte[1024];
//        StringBuffer sb = new StringBuffer();
//        while ((len = in.read(b)) > 0) {
//            sb.append(new String(b, 0, len));
//        }
//        return sb.toString();
        return new String(StreamHelper.read(in));
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
