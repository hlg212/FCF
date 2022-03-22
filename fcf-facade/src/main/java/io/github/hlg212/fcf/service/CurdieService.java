/** 
 * Project Name:frame-facade 
 * File Name:CurdService.java 
 * Package Name: io.github.hlg212.fcf.service
 * Date:2018年7月30日 上午11:15:02 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.service;

import  io.github.hlg212.fcf.ISerializable;

/**
 * 新扩展的 增、修改、查询、珊瑚、导入、导出 接口
 * 该种扩展模式不友好，后期争取优化
 *
 * @author huangligui
 * @date 2020年5月30日
 */
public interface CurdieService<T extends ISerializable> extends CurdService<T>,ExportService<T>,ImportService<T>{

	
}
