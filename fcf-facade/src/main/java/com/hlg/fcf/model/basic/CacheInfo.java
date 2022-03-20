package com.hlg.fcf.model.basic;

import com.hlg.fcf.ISerializable;

import lombok.Data;

/**
 * 
 * @author changwei
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
