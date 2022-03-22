/** 
 * Project Name:facade-system 
 * File Name:PubUser.java 
 * Package Name:com.htcf.system.model.po 
 * Date:2016年12月23日 上午11:37:42 
 * Copyright (c) 2017, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package  io.github.hlg212.fcf.model.basic;

import  io.github.hlg212.fcf.model.Model;
import lombok.Data;


@Data
public class File extends Model implements IFile {
	private String id;
	private String appId;
	private String path;
	private String bcode;
	private String fileName;
	private String contentType;
	private Long size;
	private byte[] content;
}
