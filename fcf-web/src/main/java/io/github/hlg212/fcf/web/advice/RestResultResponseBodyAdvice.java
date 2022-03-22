/**
 * 
 */
package  io.github.hlg212.fcf.web.advice;

import com.alibaba.fastjson.JSON;
import  io.github.hlg212.fcf.annotation.RestBody;
import  io.github.hlg212.fcf.constants.FrameCommonConstants;
import  io.github.hlg212.fcf.model.ActionResult;
import  io.github.hlg212.fcf.model.ActionResultBuilder;
import  io.github.hlg212.fcf.util.AccessContextHelper;
import  io.github.hlg212.fcf.util.HandlerTypePredicateHelper;
import  io.github.hlg212.fcf.web.annotation.MvcConditional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Executable;

/**
 * rest接口返回值统一包装实现
 *
 * @author huangligui
 * @date 2018年9月10日
 */
@Slf4j
@ControllerAdvice
@MvcConditional
public class RestResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {


	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		if( !HandlerTypePredicateHelper.test(returnType.getContainingClass()) ) {
			return false;
		}

		Executable e = returnType.getExecutable();
		RestBody restBody = e.getAnnotation(RestBody.class);
		if(restBody != null) {
			return restBody.value();
		}
		
		Class<?> controllerClass = returnType.getContainingClass();
		controllerClass = controllerClass == null ? e.getDeclaringClass() : controllerClass;
		restBody = controllerClass.getAnnotation(RestBody.class);
		if(restBody != null) {
			return restBody.value();
		}
		/*String pkgName = controllerClass.getName();
		List<String> scanPks = this.getScanPackages();
		for(String pk : scanPks) {
			if(pkgName.startsWith(pk)) {
				return true;
			}
		}*/
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if( byte[].class.isAssignableFrom( returnType.getParameterType() ) )
		{
			return body;
		}
		if(body == null) {
			body = ActionResultBuilder.success().build();
		} else if(!(body instanceof ActionResult)){
			body = ActionResultBuilder.success().withData(body).build();
		}
		log.trace("框架内部请求将范围值统一包装.actionResult:{}", body);
		if(StringHttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
			//TODO 此处是否可考虑根据returnType做为判断条件更合理
			body = JSON.toJSONString(body);
			log.trace("将ActionResult序列化json字符串.body:{}", body);
		}
		response.getHeaders().add(FrameCommonConstants.FEIGN_REQUEST_KEY, "true");
		if( body instanceof  ActionResult )
		{
			((ActionResult) body).setAccessLogId(AccessContextHelper.getAccessId());
		}
		return body;
	}

	/*private List<String> getScanPackages() {
		if(scanPackages != null) {
			return scanPackages;
		}
		scanPackages = AutoConfigurationPackages.get(SpringHelper.getBeanFactory());
		return scanPackages;
	}*/
}
