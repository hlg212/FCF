/** 
 * Project Name:common-web 
 * File Name:ActionResult.java 
 * Package Name: io.github.hlg212.fcf.web.model.vo
 * Date:2016-12-27 12:41:10 
 * Copyright (c) 2016, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class ActionResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2298957468403819613L;

	/**
	 * 请求结果状态
	 */
	private Boolean success;
	
	/**
	 * 请求响应码
	 */
	private String code;
	
	/**
	 * 请求响应信息
	 */
	private String msg;
	
	/**
	 * 请求响应数据
	 */
	private T data;

	/**
	 * 访问日志id
	 */
	private String accessLogId;

}
