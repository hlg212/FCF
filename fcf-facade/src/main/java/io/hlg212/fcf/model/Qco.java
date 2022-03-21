/**
 * 
 */
package io.hlg212.fcf.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import io.hlg212.fcf.annotation.Field;

/**
 *  查询属性自定义对象
 * 
 * @author huangligui
 * @date 2018年9月7日
 */

public class Qco {

	@Field(description="属性map")
	@JsonIgnore
	private Map<String,Object> propertyMap = new HashMap<>();
	
	@Field(description="查询条件")
	@JsonIgnore
	private List<QueryCondition> conditions = new ArrayList<QueryCondition>();
	@Field(description="排序方式")
	@JsonIgnore
	private List<OrderCondition> orders = new ArrayList<OrderCondition>();


	public Qco() {
	}

	/**
	 * @return the conditions
	 */
	public List<QueryCondition> getConditions() {
		return conditions;
	}
	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(List<QueryCondition> conditions) {
		this.conditions = conditions;
	}
	/**
	 * @return the orders
	 */
	public List<OrderCondition> getOrders() {
		return orders;
	}
	/**
	 * @param orders the orders to set
	 */
	public void setOrders(List<OrderCondition> orders) {
		this.orders = orders;
	}
	
	public void addOrder(OrderCondition orderCondition) {
		this.orders.add(orderCondition);
	}
	
	/**
	 * Adds the orderCondition.添加排序条件，
	 * 
	 * @param orderCondition
	 * @return the query param
	 * @author ccfhn-xiequn
	 */
	public Qco addOrderBy(OrderCondition orderCondition) {
		this.orders.add(orderCondition);
		return this;
	}
	
	/**
	 * Adds the orderCondition.添加字段排序升序条件，
	 * 
	 * @param property 属性名
	 * @return the query param
	 * @author ccfhn-xiequn
	 */
	public Qco addOrderByAsc(String property) {
		this.orders.add(OrderCondition.ASC(property));
		return this;
	}
	
	/**
	 * Adds the orderCondition.添加字段排序降序条件，
	 * 
	 * @param property 属性名
	 * @return the query param
	 * @author ccfhn-xiequn
	 */
	public Qco addOrderByDesc(String property) {
		this.orders.add(OrderCondition.DESC(property));
		return this;
	}
	
	/**
	 * Adds the condition.添加动态条件，如 "age" ">" 18
	 *
	 * @param property 属性名
	 * @param operation 操作符
	 * @param value 值
	 * @return the query param
	 */
	public Qco addCondition(String property, String operation, Object value) {
		/*
		 * if (!property.contains(QueryParam.ALIAS_SPLIT) &&
		 * !QueryParam.ROWNUM.equals(property)) { property = "t." + property; }
		 */
		conditions.add(new QueryCondition(property, operation, value));
		return this;
	}

	public Map<String, Object> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, Object> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public Qco addProperty(String property,Object value) {
		this.propertyMap.put(property,value);
		return this;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
