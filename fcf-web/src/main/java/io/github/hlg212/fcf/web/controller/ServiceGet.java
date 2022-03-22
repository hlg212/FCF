package  io.github.hlg212.fcf.web.controller;

import  io.github.hlg212.fcf.service.ExportService;
import  io.github.hlg212.fcf.service.FrameService;
import  io.github.hlg212.fcf.service.ImportService;
import  io.github.hlg212.fcf.service.QueryService;
import  io.github.hlg212.fcf.util.ExceptionHelper;
import  io.github.hlg212.fcf.util.ServiceDaoHelper;

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
