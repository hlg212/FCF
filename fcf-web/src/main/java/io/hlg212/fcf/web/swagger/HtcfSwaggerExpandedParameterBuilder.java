/**
 * 
 */
package io.hlg212.fcf.web.swagger;

import static springfox.documentation.swagger.common.SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER;

import io.hlg212.fcf.web.annotation.MvcConditional;
import io.hlg212.fcf.web.annotation.SwaggerConditional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import io.hlg212.fcf.annotation.Field;

import springfox.documentation.spi.schema.EnumTypeDeterminer;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.readers.parameter.SwaggerExpandedParameterBuilder;

/**
 *
 *
 * @author huangligui
 * @date 2018年9月10日
 */
@Component
@Order(SWAGGER_PLUGIN_ORDER + 1)
@SwaggerConditional
public class HtcfSwaggerExpandedParameterBuilder extends SwaggerExpandedParameterBuilder {

	/**
	 * @param descriptions
	 * @param enumTypeDeterminer
	 */
	@Autowired
	public HtcfSwaggerExpandedParameterBuilder(DescriptionResolver descriptions,
			EnumTypeDeterminer enumTypeDeterminer) {
		super(descriptions, enumTypeDeterminer);
	}

	@Override
	public void apply(ParameterExpansionContext context) {
		//super.apply(context);
		Optional<Field> apiModelPropertyOptional = context.findAnnotation(Field.class);
		if (apiModelPropertyOptional.isPresent()) {
			String desc = apiModelPropertyOptional.get().description();
			if(StringUtils.isNotBlank(desc)) {
				context.getParameterBuilder().description(desc);
			}
		}
	}

}
