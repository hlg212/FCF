/** 
 * Project Name:facade-system 
 * File Name:PubUser.java 
 * Package Name:com.htcf.system.model.po 
 * Date:2016年12月23日 上午11:37:42 
 * Copyright (c) 2017, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package io.hlg212.fcf.model.ga;

import io.hlg212.fcf.model.Model;
import lombok.Data;


@Data
public class Route extends Model implements  IRoute {

	protected static final long serialVersionUID = -3420200775219367645L;

	private String id;

	private String name;

	private String matchPath;

	private String nmatchPath;

	private String uri;

	private Long order;



}
