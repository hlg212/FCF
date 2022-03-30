package io.github.hlg212.fcf.web.swagger;

import io.github.hlg212.fcf.model.Qco;
import io.github.hlg212.fcf.web.annotation.SwaggerConditional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.property.bean.AccessorsProvider;
import springfox.documentation.schema.property.field.FieldProvider;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.schema.EnumTypeDeterminer;
import springfox.documentation.spring.web.readers.parameter.ExpansionContext;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterExpander;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;


/**
 * 过滤QueryProperty属性API
 * ClassName: HtcfModelAttributeParameterExpander
 * Function: TODO ADD FUNCTION.
 * Reason: TODO ADD REASON(可选).
 * date: 2018年11月8日 下午3:34:47
 *
 * @author ccfhn-xiequn
 */
@Primary
@Component
@Qualifier("modelAttributeParameterExpander")
@SwaggerConditional
public class QcoFilterModelAttributeParameterExpander extends ModelAttributeParameterExpander {

    @Autowired
    public QcoFilterModelAttributeParameterExpander(
            FieldProvider fields,
            AccessorsProvider accessors,
            EnumTypeDeterminer enumTypeDeterminer) {

        super(fields, accessors, enumTypeDeterminer);
    }

    @Override
    public List<Parameter> expand(ExpansionContext context) {
        List<Parameter> parameters = super.expand(context);
        return parameters.stream().filter(parameter -> {
            if (parameter.getName().startsWith("conditions[")
                    || parameter.getName().startsWith("orders[")
                    || parameter.getName().startsWith("propertyMap")
                    || parameter.getName().startsWith("qco.conditions[")
                    || parameter.getName().startsWith("qco.orders[")
                    || parameter.getName().startsWith("qco.propertyMap")
            ) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());

    }
}