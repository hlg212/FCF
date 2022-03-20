
package com.hlg.fcf.util;

import com.hlg.fcf.api.AppApi;
import com.hlg.fcf.context.AppContext;
import com.hlg.fcf.model.basic.App;
import com.hlg.fcf.model.basic.IApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class AppContextHelper {

	private static AppContextHelper _instance;
	@Autowired
	private AppContext appContext = null;
	
	public static String getAppId() { return getAppContext().getAppId(); }
	public static String getAppCode()
	{
		AppContext context = getAppContext();
		if( context != null ) {
			return context.getAppCode();
		}
		return null;
	}
	public static String getAppName()
	{
		AppContext context = getAppContext();
		if( context != null ) {
			return context.getAppName();
		}
		return null;
	}


	private static AppContext getAppContext()
	{
	 	if( _instance == null )
		{
			_instance = SpringUtils.getBean(AppContextHelper.class);
		}
		return _instance.appContext;
	}

	@Component
	public static class CloudAppContext implements AppContext{

		@Autowired
		private AppApi appApi;

		@Value("${spring.application.name}")
		private String appCode;
		private IApp app;
		
		@Override
		public String getAppId() {
			// TODO Auto-generated method stub
			IApp app = getApp();
			if( app != null )
			{
				return app.getId();
			}
			return null;
		}

		@Override
		public String getAppName() {
			// TODO Auto-generated method stub
			IApp app = getApp();
			if( app != null )
			{
				return app.getName();
			}
			return null;
		}
		
		private IApp getApp()
		{
			if( app == null )
			{
				app =  appApi.getAppByCode(getAppCode());

			}
			return app;
		}

		@Override
		public String getAppCode() {
			// TODO Auto-generated method stub
			return this.appCode;
		}

	}
}
