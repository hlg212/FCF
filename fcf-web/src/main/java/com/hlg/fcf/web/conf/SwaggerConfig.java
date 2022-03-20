/** 
 * Project Name:demo 
 * File Name:SwaggerConfig.java 
 * Package Name:com.htcf.conf 
 * Date:2018年7月27日 下午2:13:58 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package com.hlg.fcf.web.conf;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.hlg.fcf.annotation.RequestParamOrBody;
import com.hlg.fcf.properties.AppInfoProperties;
import com.hlg.fcf.properties.FrameProperties;
import com.hlg.fcf.properties.PackageProperties;
import com.hlg.fcf.web.annotation.SwaggerConditional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/** 
 * ClassName: SwaggerConfig
 * Function: TODO ADD FUNCTION. 
 * Reason: TODO ADD REASON(可选).
 * date: 2018年7月27日 下午2:13:58
 * 
 * @author changwei 
 */
@Slf4j
@Configuration(com.hlg.fcf.Constants.FRAME_BEAN_PREFIX + "SwaggerConfig")
@EnableSwagger2
@SwaggerConditional
@EnableConfigurationProperties(AppInfoProperties.class)
public class SwaggerConfig {

	@Autowired
	private AppInfoProperties appInfoProperties;

	@Autowired
	private PackageProperties packageProperties;

	@Autowired
	private FrameProperties frameProperties;


	@Bean
	public Docket createRestApi2() {
		log.debug("************* createRestApi Docket bean ************");
		List operationParameters = new ArrayList();
		ParameterBuilder paramb = new ParameterBuilder();
//		paramb.name("Authorization")
//				.parameterType("header")
//				.defaultValue("")
//				.description("授权码,如果需要登录,请传入bearer token")
//				.modelRef(new ModelRef("String"));
//
//		operationParameters.add(paramb.build());
		paramb.name("X-Requested-With")
				.parameterType("header")
				.defaultValue("XMLHttpRequest").hidden(true)
				.description("不要去改变,说明是ajax，不会302")
				.modelRef(new ModelRef("String"));

		operationParameters.add(paramb.build());




		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo()).select()
				.apis(getSelector())
				.paths(PathSelectors.any())

				.build()
				.securitySchemes( securitySchemes() )
                .securityContexts( securityContexts() )
				.ignoredParameterTypes(RequestParamOrBody.class)
				.globalOperationParameters(operationParameters);
	}

	private Predicate<RequestHandler> getSelector()
	{
		String packages[] = packageProperties.getBasePackage().split(",");
		Predicate<RequestHandler> p = null;
		List list = new ArrayList(packages.length);
		for( String basePackage : packages ) {
			list.add(RequestHandlerSelectors.basePackage(basePackage));
		}
		if( !list.contains(frameProperties.getBasePackage()) )
		{
			list.add(RequestHandlerSelectors.basePackage(frameProperties.getBasePackage()));
		}

		return Predicates.or(list);
	}


	private List<SecurityScheme> securitySchemes() {
		List <SecurityScheme> auths = new ArrayList();
        auths.add( new ApiKey("Authorization bearer","Authorization","header"));
		return auths;
	}

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts=new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(securityReferences())
                        .forPaths(PathSelectors.any())
                        .build());
        return securityContexts;
    }

   private  List<SecurityReference> securityReferences() {
        List<SecurityReference> lists=new ArrayList();
        lists.add( SecurityReference.builder().scopes(new AuthorizationScope[0]).reference("Authorization bearer").build() );
        return lists;
    }

	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(appInfoProperties.getName())
                .description(appInfoProperties.getDescription())
                .termsOfServiceUrl("Terms of service")
                .version(appInfoProperties.getVersion())
                .build();
    }

}
