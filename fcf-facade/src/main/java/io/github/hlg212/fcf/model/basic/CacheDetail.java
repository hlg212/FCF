package  io.github.hlg212.fcf.model.basic;

import  io.github.hlg212.fcf.ISerializable;

import lombok.Data;

/**
 * 
 * @author huangligui
 * @date 2019年1月11日
 */
@Data
public class CacheDetail implements ISerializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7252848521765785172L;
	
	private String key;
	private Integer size;
	private Long ttl;
	private String value;
}
