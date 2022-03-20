package com.hlg.fcf.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class QueryParam {

	/**
	 * pageNum: 分页参数-页码.
	 */
	private Integer pageNum = Constants.QueryParam.PAGE_NUM;

	/**
	 * pageSize: 分页参数-页大小.
	 */
	private Integer pageSize = Constants.QueryParam.PAGE_SIZE;


	/**
	 * 自定参数
	 */
	private Object customParam;


	/**
	 * conditions: 动态添加的条件
	 */
	private List<QueryCondition> conditions = new ArrayList<QueryCondition>();

	/**
	 * sqlConditions: 人工sql条件，用于复杂SQL情形.
	 */
	private List<String> sqlConditions = new ArrayList<String>();

	private List<OrderCondition> orderConditions = new ArrayList();

	/**
	 * EQUALS: 等于符号.
	 */
	public static final String EQUALS = "=";

	/**
	 * NOT_EQUALS: 不等于符号.
	 */
	public static final String NOT_EQUALS = "!=";

	/**
	 * IS: is符号.
	 */
	public static final String IS = "is";

	/**
	 * IN: in符号.
	 */
	public static final String IN = "in";
	
	/**
	 * IN: not in符号.
	 */
	public static final String NOT_IN = "not in";

	/**
	 * LIKE: like符号.
	 */
	public static final String LIKE = "like";

	/**
	 * LIKE: like符号.
	 */
	public static final String LT_LIKE = "LtLike";

	/**
	 * LIKE: like符号.
	 */
	public static final String RT_LIKE = "RtLike";

	/**
	 * NULL: null符号.
	 */
	public static final String NULL = "null";

	/**
	 * NOT_NULL: not null符号.
	 */
	public static final String NOT_NULL = "not null";
	
	/**
	 * GREATER_THAN: 大于
	 */
	public static final String GREATER_THAN = ">";
	
	/** 
	 * GREATER_THAN_OR_EQUAL:大于等于
	 */
	public static final String GREATER_THAN_OR_EQUAL = ">=";
	
	/** 
	 * LESS_THAN:小于
	 */
	public static final String LESS_THAN = "<";
	
	/** 
	 * LESS_THAN_OR_EQUAL:小于等于
	 */
	public static final String LESS_THAN_OR_EQUAL = "<=";

	public static final String BETWEEN = "between";
	
	public static final String SORT = "sort";
	
	public static final String ALIAS_SPLIT = ".";

	public static final String ROWNUM = "rownum";

	public static final String DESC = "DESC";

	public static final String ASC = "ASC";
	/**
	 * Instantiates a new query param.
	 */
	public QueryParam() {
	}

	/**
	 * Instantiates a new query param.
	 *
	 * @param pageNum the page num
	 * @param pageSize the page size
	 */
	public QueryParam(Integer pageNum, Integer pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	
	/**
	 * Adds the condition.添加动态条件，如 "age" ">" 18
	 *
	 * @param property 属性名
	 * @param operation 操作符
	 * @param value 值
	 * @return the query param
	 */
	public QueryParam addCondition(String property, String operation, Object value) {
		/*
		 * if (!property.contains(ALIAS_SPLIT) && !ROWNUM.equals(property)) { property =
		 * "t." + property; }
		 */

		conditions.add(new QueryCondition(property, operation, value));
		return this;
	}

}
