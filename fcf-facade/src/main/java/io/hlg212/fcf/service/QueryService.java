/** 
 * Project Name:frame-facade 
 * File Name:QueryService.java 
 * Package Name:io.hlg212.fcf.service 
 * Date:2018年7月30日 上午11:12:02 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package io.hlg212.fcf.service;

import io.hlg212.fcf.ISerializable;
import io.hlg212.fcf.Query;
import io.hlg212.fcf.model.Qco;

/** 
 * ClassName: QueryService
 * Function: TODO ADD FUNCTION. 
 * Reason: TODO ADD REASON(可选).
 * date: 2018年7月30日 上午11:12:02
 * 
 * @author huangligui 
 */
public interface QueryService<T extends ISerializable> extends Query<T, Qco>,FrameService{

	
}
