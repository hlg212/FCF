/**
 * 
 */
package io.github.hlg212.fcf.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.hlg212.fcf.ISerializable;
import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.annotation.PkId;
import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;


/**
 * 公共通用字段
 *
 * @author huangligui
 * @date 2018年9月18日
 */
@Data
public class BaseModel extends Model{
	@PkId
	private String id;

	@Field(description="备注")
	private String memo;

	@Field(description="排序")
	private Integer sortNum;

	@Field(description="创建时间")
	private Date createTime;

	@Field(description="创建人名称")
	private String createUserName;

	@Field(description="修改时间")
	private Date updateTime;

	@Field(description="修改人名称")
	private String updateUserName;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
