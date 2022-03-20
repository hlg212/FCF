/** 
 * Project Name:facade-system 
 * File Name:PubUser.java 
 * Package Name:com.htcf.system.model.po 
 * Date:2016年12月23日 上午11:37:42 
 * Copyright (c) 2017, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package com.hlg.fcf.model.basic;

import com.hlg.fcf.model.Model;
import lombok.Data;


@Data
public class Org extends Model implements  IOrg {

	protected static final long serialVersionUID = -3420200775219367645L;

	private String id;

	private String name;

	private String code;

}
