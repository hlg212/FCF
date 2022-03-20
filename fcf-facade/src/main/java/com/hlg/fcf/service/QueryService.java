/** 
 * Project Name:frame-facade 
 * File Name:QueryService.java 
 * Package Name:com.hlg.fcf.service 
 * Date:2018年7月30日 上午11:12:02 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package com.hlg.fcf.service;

import com.hlg.fcf.ISerializable;
import com.hlg.fcf.Query;
import com.hlg.fcf.model.Qco;

/** 
 * ClassName: QueryService
 * Function: TODO ADD FUNCTION. 
 * Reason: TODO ADD REASON(可选).
 * date: 2018年7月30日 上午11:12:02
 * 
 * @author changwei 
 */
public interface QueryService<T extends ISerializable> extends Query<T, Qco>,FrameService{

	
}
