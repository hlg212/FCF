/** 
 * Project Name:frame-facade 
 * File Name:LongRunningRes.java 
 * Package Name:io.hlg212.fcf.model.ga
 * Date:2020年3月28日上午9:27:07 
 * Copyright (c) 2020, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package io.hlg212.fcf.model.ga;

import io.hlg212.fcf.model.Model;

import lombok.Data;

/** 
 * ClassName: LongRunningRes <br/> 
 * Function: 耗时资源  <br/> 
 * date: 2020年3月28日 上午9:27:07 <br/> 
 * 
 * @author pengdz 
 * @version 
 */
@Data
public class LongRunningRes extends Model implements ILongRunningRes {
	protected static final long serialVersionUID = -3420200775219367641L;
	
	private String id;

	private String name;

	private Integer timeout;

	private String uri;

}
