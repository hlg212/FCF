/** 
 * Project Name:frame-facade 
 * File Name:ILongRunningRes.java 
 * Package Name:com.hlg.fcf.model 
 * Date:2020年3月28日上午9:22:28 
 * Copyright (c) 2020, 航天长峰湖南分公司  All Rights Reserved. 
 * 
 */
package com.hlg.fcf.model.ga;

import com.hlg.fcf.ISerializable;

/** 
 * ClassName: ILongRunningRes <br/> 
 * 耗时资源 <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2020年3月28日 上午9:22:28 <br/> 
 * 
 * @author pengdz 
 * @version 
 */
public interface ILongRunningRes extends ISerializable {

    public String getId();

    public String getName();

    public String getUri();
    
    public Integer getTimeout(); 
}
