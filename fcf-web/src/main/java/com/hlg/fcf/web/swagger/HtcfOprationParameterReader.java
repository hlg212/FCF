package com.hlg.fcf.web.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.hlg.fcf.annotation.RequestParamOrBody;
import com.hlg.fcf.web.annotation.SwaggerConditional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.readers.parameter.ExpansionContext;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterExpander;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * 重写web框架注解对应在swagger容器中参数解析器
 * @author changwei
 */
@Component
@SwaggerConditional
public class HtcfOprationParameterReader implements OperationBuilderPlugin {

    @Autowired
    private DocumentationPluginsManager pluginsManager;

    @Autowired
    private ModelAttributeParameterExpander expander;

    @Override
    public void apply(OperationContext context) {
        context.operationBuilder().parameters(readParameters(context));
    }

    private List<Parameter> readParameters(final OperationContext context) {
        List<ResolvedMethodParameter> methodParameters = context.getParameters();
        List<Parameter> parameters = newArrayList();
        for (ResolvedMethodParameter methodParameter : methodParameters) {
            if(!methodParameter.hasParameterAnnotation(RequestParamOrBody.class)){
                continue;
            }
            String method = context.httpMethod().name();
            ResolvedType alternate = context.alternateFor(methodParameter.getParameterType());
            if( "GET".equalsIgnoreCase(method) )
            {
                parameters.addAll( expander.expand(
                        new ExpansionContext("", alternate, context)));
            }
            else
            {
                ParameterContext parameterContext = new ParameterContext(methodParameter,
                        new ParameterBuilder(),
                        context.getDocumentationContext(),
                        context.getGenericsNamingStrategy(),
                        context);
                parameters.add(pluginsManager.parameter(parameterContext));
            }

        }
        return parameters;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}
