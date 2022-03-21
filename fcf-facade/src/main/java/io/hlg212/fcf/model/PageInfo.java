/** 
 * Project Name:frame-facade 
 * File Name:PageInfo.java 
 * Package Name:io.hlg212.fcf.model 
 * Date:2018年8月8日 下午4:06:41 
 * Copyright (c) 2018, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package io.hlg212.fcf.model;

import java.io.Serializable;
import java.util.List;

import io.hlg212.fcf.annotation.Field;
import io.hlg212.fcf.annotation.Fields;
import lombok.Data;

/** 
 * ClassName: PageInfo </br>
 * Function: TODO ADD FUNCTION. </br>
 * Reason: TODO ADD REASON(可选). </br>
 * date: 2018年8月8日 下午4:06:41
 * 
 * @author huangligui 
 */
@Fields({
	@Field(name="pageNum", description="当前页"),
	@Field(name="pageSize", description="每页的数量"),
	@Field(name="size", description="当前页的数量"),
	@Field(name="orderBy", description="排序"),
	@Field(name="total", description="总记录数"),
	@Field(name="pages", description="总页数"),
	@Field(name="list", description="结果集"),
})
@Data
public class PageInfo<T> implements Serializable{
	private int pageNum = 1;
	private int pageSize = 10;
	private String orderBy;
	private long total;
	private int pages;
	private int size;
	private List<T> list;

	public PageInfo(int pageNum, int pageSize, long total, List<T> list) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.total = total;
		this.list = list;
		if( list != null ) {
			this.size = list.size();
		}
		if( total > 0 )
		{
			this.pages = (int)Math.ceil(total * 1.0 / pageSize);
		}

	}

	public PageInfo(List<T> list) {
		this(0,10,0,list);
		this.list = list;
	}
	public PageInfo() {
	}
}
