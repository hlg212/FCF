/** 
 * Project Name:frame-web 
 * File Name:CurdController.java 
 * Package Name: io.github.hlg212.fcf.controller
 * Date:2018年8月16日 下午12:56:42 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.web.controller;

import  io.github.hlg212.fcf.ISerializable;
import  io.github.hlg212.fcf.model.Qco;


/**
 * 增删改查导入导出控制器
 * 也可通过下列接口，自由搭配组合
 *
 * @see QueryController
 * @see CurdController
 * @see ImportController
 * @see ExportController
 * @author huangligui
 * @date 2020年8月18日
 */
public interface CurdieController<T extends ISerializable, Q extends Qco> extends CurdController<T,Q>,ImportController<T, Q>,ExportController<T,Q>{



}