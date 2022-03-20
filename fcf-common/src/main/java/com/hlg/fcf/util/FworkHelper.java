package com.hlg.fcf.util;

import com.hlg.fcf.cache.Constants;
import com.hlg.fcf.context.FworkContext;
import com.hlg.fcf.model.basic.IUser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/** 
 * 返回一些常用的信息
 * 比如当前用户、机构
 * http,dubbo 线程都可以直接使用
 * 
 * ClassName: FworkHelper
 * Function: TODO ADD FUNCTION. 
 * Reason: 框架工具
 * date: 2017年8月23日 上午8:50:16
 * 
 * @author huangligui 
 */
@Component
@CacheConfig(cacheNames = {Constants.FworkHelper},cacheManager = Constants.CacheManager.ThreadLocalCacheManager)
public class FworkHelper {
	private static final String FWORK_CONTEXT = "FWORK_CONTEXT";

	private static final String FWORK_CONTEXT_USER = "'FWORK_CONTEXT_USER'";

	private static FworkHelper _instance;


	private static FworkHelper getInstance()
	{
		if( _instance == null )
		{
			_instance = SpringUtils.getBean(FworkHelper.class);
		}
		return _instance;
	}


	/**
	 * 获得当前用户
	 * 
	 * @return
	 */
	public static IUser getUser()
	{
		return getInstance()._getUser();
	}

	@Cacheable(key = FWORK_CONTEXT_USER)
	public IUser _getUser()
	{
		FworkContext context = getContext();
		if( context != null ) {
			return context.getUser();
		}
		return null;
	}
	
	public static String getAccount()
	{
		IUser user = getUser();
		if( user != null ) {
			return user.getAccount();
		}
		return  null;
	}

	public static String getOrgId()
	{
		IUser user = getUser();
		if( user != null ) {
			return user.getOrgId();
		}
		return null;
	}

	public static String getUid()
	{
		IUser user = getUser();
		if( user != null ) {
			return user.getId();
		}
		return  null;
	}

	public static String geUserName()
	{
		IUser user = getUser();
		if( user != null ) {
			return user.getName();
		}
		return  null;
	}
	
	public static Object get(String key)
	{
		FworkContext context = getContext();
		if( context != null ) {
			return context.get(key);
		}
		return null;
	}


	public static void setContext(FworkContext context)
	{
		ThreadLocalHelper.set(FWORK_CONTEXT, context);
	}
	
	public static FworkContext getContext()
	{
		return (FworkContext)ThreadLocalHelper.get(FWORK_CONTEXT);
	}
}
