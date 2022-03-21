package io.hlg212.fcf.web.swagger;

import com.fasterxml.classmate.ResolvedType;
import io.hlg212.fcf.annotation.RequestParamOrBody;
import io.hlg212.fcf.web.annotation.SwaggerConditional;
import org.springframework.stereotype.Component;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationModelsProviderPlugin;
import springfox.documentation.spi.service.contexts.RequestMappingContext;

import java.util.List;

@Component
@SwaggerConditional
public class RequestParamOrBodyOperationModelsProvider implements OperationModelsProviderPlugin {

    @Override
    public void apply(RequestMappingContext context) {
        List<ResolvedMethodParameter> parameterTypes = context.getParameters();
        for (ResolvedMethodParameter parameterType : parameterTypes) {
            if (parameterType.hasParameterAnnotation(RequestParamOrBody.class)) {
                ResolvedType modelType = context.alternateFor(parameterType.getParameterType());
                context.operationModelsBuilder().addInputParam(modelType);
            }
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}
