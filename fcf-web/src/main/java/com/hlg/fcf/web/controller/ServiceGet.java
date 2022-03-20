package com.hlg.fcf.web.controller;

import com.hlg.fcf.service.ExportService;
import com.hlg.fcf.service.FrameService;
import com.hlg.fcf.service.ImportService;
import com.hlg.fcf.service.QueryService;
import com.hlg.fcf.util.ExceptionHelper;
import com.hlg.fcf.util.ServiceDaoHelper;

public interface ServiceGet {

    default  <S extends FrameService>S  getService()
    {
        S service = (S)getService(QueryService.class);
        if( service != null )
        {
            return service;
        }
        service = (S)getService(ExportService.class);
        if( service != null )
        {
            return service;
        }
        service = (S)getService(ImportService.class);
        if( service != null )
        {
            return service;
        }
        return null;
    }

    default  <S>S  getService(Class<S> serviceClass)
    {
        try {
            return serviceClass.cast(ServiceDaoHelper.getService(getClass(),serviceClass));
        }catch(ClassCastException e) {
            ExceptionHelper.throwServerException("无法转换成指定service!",e);
        }
        return null;
    }
}
