/**
 * 
 */
package io.hlg212.fcf.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.hlg212.fcf.ISerializable;


/**
 * 数据库实体基类
 *
 * @author huangligui
 * @date 2018年9月18日
 */
@JsonSerialize
public class Model implements ISerializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 645693793561786797L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
