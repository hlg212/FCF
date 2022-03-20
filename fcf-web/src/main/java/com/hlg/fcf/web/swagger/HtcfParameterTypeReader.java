package com.hlg.fcf.web.swagger;

import com.hlg.fcf.annotation.RequestParamOrBody;
import com.hlg.fcf.web.annotation.SwaggerConditional;
import org.springframework.stereotype.Component;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

/**
 * 定义web框架注解对应在swagger容器中的参数类型值
 * @author changwei
 */
@Component
@SwaggerConditional
public class HtcfParameterTypeReader implements ParameterBuilderPlugin {

    @Override
    public void apply(ParameterContext parameterContext) {
        ResolvedMethodParameter resolvedMethodParameter = parameterContext.resolvedMethodParameter();
        if(resolvedMethodParameter.hasParameterAnnotation(RequestParamOrBody.class)){
            String method =  parameterContext.getOperationContext().httpMethod().name();
            if( "GET".equalsIgnoreCase(method) )
            {
                parameterContext.parameterBuilder().parameterType("query");
            }
           else
            {
                parameterContext.parameterBuilder().parameterType("body");
            }

        }

    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}
