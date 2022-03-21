/** 
 * Project Name:facade-system 
 * File Name:PubUser.java 
 * Package Name:com.htcf.system.model.po 
 * Date:2016年12月23日 上午11:37:42 
 * Copyright (c) 2017, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package io.hlg212.fcf.model.basic;

import io.hlg212.fcf.model.Model;
import lombok.Data;

import java.util.List;

@Data
public class Res extends Model implements  IRes,ITree {

	protected static final long serialVersionUID = -3420200775219367645L;

	private String name;
	private String type;
	private String code;
	private String path;
	private String icon;
	private String id;
	private String pid;
	private List<Res> children;

}
