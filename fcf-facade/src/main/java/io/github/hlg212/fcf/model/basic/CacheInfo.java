package  io.github.hlg212.fcf.model.basic;

import  io.github.hlg212.fcf.ISerializable;

import lombok.Data;

/**
 * 
 * @author huangligui
 * @date 2019年1月11日
 */
@Data
public class CacheInfo implements ISerializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3015056298206916471L;
	
	private String cacheName;
	private Long cacheCount;
	private Long cacheSize;
	private String ipAddress;
	private String cacheType;
}
