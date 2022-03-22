package  io.github.hlg212.fcf.service.impl;

import  io.github.hlg212.fcf.Export;
import  io.github.hlg212.fcf.model.*;
import  io.github.hlg212.fcf.model.basic.File;
import  io.github.hlg212.fcf.service.FrameService;
import  io.github.hlg212.fcf.service.QueryService;
import  io.github.hlg212.fcf.util.*;

import java.util.Collections;
import java.util.List;


/**
 * 导出接口
 * 如果是需要自定义实现，只需要接口时使用
 * 常规模块开发，请使用 CurdieService
 *
 * 默认导出为 excel格式，如果想使用其它格式，请覆盖 getExportTransform;
 *
 * @see ExportTransform
 * @see CurdieServiceImpl
 * @author huangligui
 * @date 2020年8月18日
 */
public interface ExportServiceImpl<T extends Model> extends  io.github.hlg212.fcf.service.ExportService<T>{

    @Override
    default public File getExportTemplate() {
        return getExportTransform().export(Collections.emptyList());
    }

    @Override
    default  public File exportPage(PageQuery pageQuery) {
        QueryService queryService = ServiceDaoHelper.getService(this.getClass(),QueryService.class);
        PageInfo pageInfo = queryService.findPage(pageQuery.getQco(), pageQuery.getPageNum(), pageQuery.getPageSize());
        List datas = pageInfo.getList();
        return export(datas);
    }

    @Override
    default  public File export(List datas ) {
        return getExportTransform().export(datas);
    }


    default ExportTransform getExportTransform()
    {
        String cacheId = this.getClass().getName()+Constants.EXPORTTRANSFORM_CACHE_SUFFIX;
        ExportTransform exportTransform = CacheDataHelper.get(cacheId);
        if( exportTransform == null )
        {
            synchronized (cacheId) {
                exportTransform = CacheDataHelper.get(cacheId);
                if( exportTransform == null ) {

                    try {
                        exportTransform = SpringHelper.getBeanByResolvableType(getClass(), ExportTransform.class);
                    }catch (Exception e){
                    }
                    if( exportTransform == null )
                    {
                        LogHelper.getLog(getClass()).info("没有声明导出转换接口,将采用默认的接口!");
                        Class c = ParameterizedTypeHelper.getParameterizedType(getClass(), Model.class);
                        exportTransform = new XlsExportTransform(c);
                    }
                    CacheDataHelper.put(cacheId, exportTransform);
                }
            }
        }
        return exportTransform;
    }


}
