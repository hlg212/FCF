package com.hlg.fcf.web.swagger;

import com.hlg.fcf.model.Qco;
import com.hlg.fcf.web.annotation.SwaggerConditional;
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
public class HtcfModelAttributeParameterExpander extends ModelAttributeParameterExpander{
                          
	@Autowired
	public HtcfModelAttributeParameterExpander(
	      FieldProvider fields,
	      AccessorsProvider accessors,
	      EnumTypeDeterminer enumTypeDeterminer) {

		super(fields, accessors, enumTypeDeterminer);
	 }
	 
	@Override
	public List<Parameter> expand(ExpansionContext context) {
		
		/*Class<?> queryProperty = null;
		try {
			queryProperty = Class.forName("com.hlg.fcf.model.QueryProperty");
		} catch (ClassNotFoundException e) {
			log.error("NotFound"+"com.hlg.fcf.model.QueryProperty");
			return super.expand(context);
		}*/
		Class<?> queryProperty = Qco.class;
		Class<?> target_object = context.getParamType().getErasedType();
		
		if (queryProperty.isAssignableFrom(target_object)){
			
			if(queryProperty.getName().equals(target_object.getName())) {
				
				//return newArrayList();				
				return super.expand(context);
			}else {
								
				/* Set<PropertyDescriptor> propertyDescriptors = propertyDescriptors(context.getParamType().getErasedType());
				    *    因为方放都是私有的，不改源代码就只能二次筛选。
				 */
				List<Parameter> filter_parameters = newArrayList();
				super.expand(context).forEach(param->{
					if(param.getName().startsWith("conditions[")||
						param.getName().startsWith("orders[") ||
							param.getName().startsWith("propertyMap")
					)
					{
						return;
					}
					filter_parameters.add(param);					
				});
				
				return filter_parameters;
			}
			
		}else {
			return super.expand(context);
		}		
		
	}
}