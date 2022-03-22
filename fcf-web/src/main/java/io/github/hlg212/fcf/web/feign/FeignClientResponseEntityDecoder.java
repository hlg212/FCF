/** 
 * Project Name:spring-boot-web 
 * File Name:FeignClientResponseEntityDecoder.java 
 * Package Name: io.github.hlg212.fcf.web.conf
 * Date:2018年3月15日 上午9:17:53 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.web.feign;

import com.alibaba.fastjson.JSON;
import  io.github.hlg212.fcf.constants.FrameCommonConstants;
import  io.github.hlg212.fcf.model.ActionResult;
import  io.github.hlg212.fcf.model.ActionResultBuilder;
import  io.github.hlg212.fcf.util.ExceptionHelper;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Map;

/** 
 * ClassName: FeignClientResponseEntityDecoder
 * Function: TODO ADD FUNCTION. 
 * Reason: TODO ADD REASON(可选).
 * date: 2018年3月15日 上午9:17:53
 * 
 * @author huangligui 
 */
@Slf4j
public class FeignClientResponseEntityDecoder extends ResponseEntityDecoder{

	/** 
	 * Creates a new instance of FeignClientResponseEntityDecoder. 
	 * 
	 * @param decoder 
	 */
	public FeignClientResponseEntityDecoder(Decoder decoder) {
		super(decoder);
	}
	
	@Override
	public Object decode(Response response, Type type) throws IOException, FeignException {
		Map<String, Collection<String>> headers = response.headers();
		Collection<String> header = headers.get(FrameCommonConstants.FEIGN_REQUEST_KEY);
		if(header == null || header.isEmpty()) {
			log.trace("非框架内部发起的请求，直接返回不做任务处理");
			return super.decode(response, type);
		}
		if(type instanceof TypeVariable) {
			//feign 接口为动态泛型类
			type = ((TypeVariable<?>)type).getBounds()[0];
		}
		String tempResult = (String) super.decode(response, String.class);
		HtcfActionResult result = JSON.parseObject(tempResult, HtcfActionResult.class);
		if(false == result.getSuccess()) {
			String code = result.getCode();
			int status = ActionResultBuilder.ActionResultStatus.INTERNAL_SERVER_ERROR.getCode();
			try {
				status = Integer.parseInt(code);
			}catch(Exception e) {}
			//response = response.toBuilder().status(status).body(result.getData(), Charset.forName("UTF-8")).build();
			//throw FeignException.errorStatus(response.request().url(), response);
			if( ActionResultBuilder.ActionResultStatus.BUSINESS_ERROR.getCode() == status )
			{
				ExceptionHelper.throwBusinessException(result.getMsg());
			}
			else
			{
				ExceptionHelper.throwException(status,result.getMsg(),result.getData());
			}

		}
		if(String.class.toString().equals(String.valueOf(type))) {
			return result.getData();
		}
		return JSON.parseObject(result.getData(), type);
	}

	private static class HtcfActionResult extends ActionResult<String>
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}
	
}
