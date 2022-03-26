
package  io.github.hlg212.fcf.model.basic;

import io.github.hlg212.fcf.ISerializable;
import io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

@Data
public class PoInfo implements ISerializable {
	@Field(description = "名称")
    private String name;
	@Field(description = "简介")
	private String desc;
	@Field(description = "主键")
	private String primarykey;
	@Field(description = "业务编码")
	private String businessCode;
	@Field(description = "属性明细")
	private String attributeDetails;
}
