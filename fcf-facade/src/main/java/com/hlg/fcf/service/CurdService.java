/** 
 * Project Name:frame-facade 
 * File Name:CurdService.java 
 * Package Name:com.hlg.fcf.service 
 * Date:2018年7月30日 上午11:15:02 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package com.hlg.fcf.service;

import com.hlg.fcf.Curd;
import com.hlg.fcf.ISerializable;
import com.hlg.fcf.model.Qco;

public interface CurdService<T extends ISerializable> extends QueryService<T>,Curd<T, Qco> {

	
}
