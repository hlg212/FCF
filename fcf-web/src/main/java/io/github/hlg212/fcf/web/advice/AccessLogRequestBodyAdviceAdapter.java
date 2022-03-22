package  io.github.hlg212.fcf.web.advice;

import com.alibaba.fastjson.JSON;
import  io.github.hlg212.fcf.util.ThreadLocalHelper;
import  io.github.hlg212.fcf.web.annotation.MvcConditional;
import  io.github.hlg212.fcf.web.filter.AccessLogFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@Slf4j
@ControllerAdvice(basePackages=" io.github.hlg212")
@MvcConditional
public class AccessLogRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        body = super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
        ThreadLocalHelper.set(AccessLogFilter.REQUEST_BODY_PARAM,JSON.toJSONString(body));
        return body;
    }

}
