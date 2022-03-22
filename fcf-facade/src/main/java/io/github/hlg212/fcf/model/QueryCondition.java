
package  io.github.hlg212.fcf.model;

import org.apache.commons.lang.StringUtils;

import  io.github.hlg212.fcf.annotation.Field;
import  io.github.hlg212.fcf.exception.BaseException;


public class QueryCondition {

	/**
	 * property: 属性名.
	 */
	@Field(description="属性名")
	private String property;

	/**
	 * operation: 操作符.
	 */
	@Field(description="操作符，默认值=")
	private String operation /*= QueryParam.EQUALS*/;

	/**
	 * value: 属性值.
	 */
	@Field(description="属性值")
	private Object value;

	@Field(description="条件前缀，当出现多表查询同名字段时指定")
	private String prefix;
	
	public QueryCondition() {
		
	}

	/**
	 * Instantiates a new query condition.
	 *
	 * @param property the property
	 * @param operation the operation
	 * @param value the value
	 */
	public QueryCondition(String property, String operation, Object value) {
		this.property = property;
		this.operation = operation;
		this.value = value;
		this.check();
	}

	/**
	 * Gets the property.
	 *
	 *   the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * Sets the property.
	 *
	 * @param property the new property
	 */
	public void setProperty(String property) {
		this.checkProperty(property);
		this.property = property;
	}

	/**
	 * Gets the operation.
	 *
	 *   the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * Sets the operation.
	 *
	 * @param operation the new operation
	 */
	public void setOperation(String operation) {
		this.checkOperation(operation);
		this.operation = operation;
	}
	
	private void checkOperation(String operation) {
		if(StringUtils.isBlank(operation)) {
			throw new BaseException(ActionResultBuilder.ActionResultStatus.BUSINESS_ERROR.getCode(), "operation 不能为空");
		}
		if(!QcoSuffix.containsOperation(operation) && !QueryParam.EQUALS.equals(operation)) {
//		if(!QueryParam.EQUALS.equals(operation) &&
//				!QueryParam.GREATER_THAN.equals(operation) &&
//				!QueryParam.GREATER_THAN_OR_EQUAL.equals(operation) &&
//				!QueryParam.IN.equals(operation) &&
//				!QueryParam.IS.equals(operation) && 
//				!QueryParam.LESS_THAN.equals(operation) && 
//				!QueryParam.LESS_THAN_OR_EQUAL.equals(operation) && 
//				!QueryParam.LIKE.equals(operation) && 
//				!QueryParam.NOT_EQUALS.equals(operation) && 
//				!QueryParam.NOT_IN.equals(operation) && 
//				!QueryParam.NOT_NULL.equals(operation) && 
//				!QueryParam.NULL.equals(operation)) {
			throw new BaseException(ActionResultBuilder.ActionResultStatus.BUSINESS_ERROR.getCode(), "operation无效"+operation+"，传入非定义操作符");
		}
	}
	
	private void checkProperty(String property) {
		if(StringUtils.isBlank(property)) {
			throw new BaseException(ActionResultBuilder.ActionResultStatus.BUSINESS_ERROR.getCode(), "property 不能为空");
		}
	}
	
	private void checkValue(Object value) {
		if(value == null) {
			if( StringUtils.isNotEmpty( property ) )
			{
				throw new BaseException(ActionResultBuilder.ActionResultStatus.BUSINESS_ERROR.getCode(), String.format("查询条件 [%s] value 不能为空",property));
			}
			else
			{
				throw new BaseException(ActionResultBuilder.ActionResultStatus.BUSINESS_ERROR.getCode(), "查询条件 value 不能为空");
			}
		}
	}
	
	public QueryCondition check() {
		this.checkProperty(this.property);
		this.checkValue(this.value);
		this.checkOperation(this.operation);
		
		if(QueryParam.IS.equals(this.operation)) {
			String valueStr = value.toString().trim();
			if(!QueryParam.NULL.equalsIgnoreCase(valueStr) 
					&& !QueryParam.NOT_NULL.equalsIgnoreCase(valueStr)) {
				throw new BaseException(ActionResultBuilder.ActionResultStatus.BUSINESS_ERROR.getCode(), this.property+" is "+valueStr+" 条件设置有误,值只能为null或not null");
			}
		}
		return this;
	}
	

	/**
	 * Gets the value.
	 *
	 *   the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Object value) {
		this.checkValue(value);
		this.value = value;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.property).append(" ");
		sb.append(this.operation).append(" ");
		sb.append(this.value);
		return sb.toString();
	}


	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
