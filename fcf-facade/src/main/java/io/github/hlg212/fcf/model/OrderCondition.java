
package  io.github.hlg212.fcf.model;

import  io.github.hlg212.fcf.annotation.Field;

public class OrderCondition {
	
	@Field(description="排序字段名")
	private String name;
	
	@Field(description="排序方式值asc|desc")
	private String value;

	@Field(description="条件前缀，当出现多表查询同名字段时指定")
	private String prefix;
	
	public OrderCondition() {
		
	}
	
	public OrderCondition(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 *   the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *   the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/** 
	 * ASC:"asc"  升序
	 * @author ccfhn-xiequn
	 */
	public static final String ASC = "asc";
	
	/** 
	 * DESC:"desc" 降序
	 * @author ccfhn-xiequn
	 */ 
	public static final String DESC = "desc";
	
	/**
	 * 设置指定字段排序为升序
	 * 
	 * @param name
	 *
	 * @author ccfhn-xiequn
	 */
	public OrderCondition setAsc(String name) {
		this.name = name;
		this.value = ASC;
		return this;
	}
	
	/**
	 * 设置指定字段排序为降序
	 * 
	 * @param name
	 *
	 * @author ccfhn-xiequn
	 */
	public OrderCondition setDesc(String name) {
		this.name = name;
		this.value = DESC;
		return this;
	}
	
	/**
	 * 获取指定字段为升序得排序对象
	 * 
	 * @param name
	 *
	 * @author ccfhn-xiequn
	 */
	public static OrderCondition ASC(String name) {
		return new OrderCondition(name, ASC);
	}
	
	/**
	 * 获取指定字段为降序得排序对象
	 * 
	 * @param name
	 *
	 * @author ccfhn-xiequn
	 */
	public static OrderCondition DESC(String name) {
		return new OrderCondition(name, DESC);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
