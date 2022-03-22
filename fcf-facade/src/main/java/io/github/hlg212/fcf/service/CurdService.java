/** 
 * Project Name:frame-facade 
 * File Name:CurdService.java 
 * Package Name: io.github.hlg212.fcf.service
 * Date:2018年7月30日 上午11:15:02 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.service;

import  io.github.hlg212.fcf.Curd;
import  io.github.hlg212.fcf.ISerializable;
import  io.github.hlg212.fcf.model.Qco;

public interface CurdService<T extends ISerializable> extends QueryService<T>,Curd<T, Qco> {

	
}
