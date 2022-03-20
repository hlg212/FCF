/** 
 * Project Name:common-base 
 * File Name:BaseExcetpion.java 
 * Package Name:com.hlg.fcf.base.exception 
 * Date:2016-12-27 15:57:42 
 * Copyright (c) 2016, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package com.hlg.fcf.exception;

/**
 * ClassName: BaseExcetpion Function: 异常基类 date: 2016年12月12日 上午10:33:11 .
 *
 * @author chenshizhe
 */
public class BaseException extends RuntimeException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5875371379845226068L;

	/**
	 * 异常信息.
	 */
	protected String msg;

	/**
	 * 具体异常码.
	 */
	protected int code;

	private Object data;

	/**
	 * Instantiates a new base excetpion.
	 *
	 * @param code the code
	 * @param msgFormat the msg format
	 * @param args the args
	 */
	public BaseException(int code, String msgFormat, Object... args) {
		super(String.format(msgFormat == null ? "" : msgFormat, args));
		this.code = code;
		this.msg = String.format(msgFormat == null ? "" : msgFormat, args);
	}
	
	/**
	 * Instantiates a new base excetpion.
	 *
	 * @param code the code
	 * @param msgFormat the msg format
	 * @param args the args
	 */
	public BaseException(Throwable cause, int code, String msgFormat, Object... args) {
		super(String.format(msgFormat == null ? "" : msgFormat, args), cause);
		this.code = code;
		this.msg = String.format(msgFormat == null ? "" : msgFormat, args);
	}

	/**
	 * Instantiates a new base excetpion.
	 */
	public BaseException() {
		super();
	}

	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 实例化异常.
	 *
	 * @param msgFormat the msg format
	 * @param args the args
	 * @return the base excetpion
	 */
	public BaseException newInstance(String msgFormat, Object... args) {
		return new BaseException(this.code, msgFormat, args);
	}

	/**
	 * Instantiates a new base excetpion.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new base excetpion.
	 *
	 * @param cause the cause
	 */
	public BaseException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new base excetpion.
	 *
	 * @param message the message
	 */
	public BaseException(String message) {
		super(message);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
