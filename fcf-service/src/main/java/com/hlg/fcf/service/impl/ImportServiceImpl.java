package com.hlg.fcf.service.impl;

import com.hlg.fcf.api.FileApi;
import com.hlg.fcf.model.ImpExpModel;
import com.hlg.fcf.model.Model;
import com.hlg.fcf.model.basic.File;
import com.hlg.fcf.util.CacheDataHelper;
import com.hlg.fcf.util.ExceptionHelper;
import com.hlg.fcf.util.ParameterizedTypeHelper;
import com.hlg.fcf.util.SpringUtils;

import java.util.Collection;

/**
 * 导入接口
 * 如果是需要自定义实现，只需要接口时使用
 * 常规模块开发，请使用 CurdieService

 * 默认为 excel格式，如果想使用其它格式，请覆盖 getImportTransform
 * 尽量重写 importSave 方法，覆盖默认的保存逻辑
 *
 * @see ExportTransform
 * @see CurdieServiceImpl
 * @author huangligui
 * @date 2020年8月18日
 */
public interface ImportServiceImpl<T extends Model> extends com.hlg.fcf.service.ImportService<T>{

    @Override
    default  public ImpExpModel importParse(File file) {
        if (file.getContent() == null || file.getContent().length == 0) {
            FileApi fileApi = SpringUtils.getBean(FileApi.class);
            file = fileApi.download(file.getPath());
            if (file == null) {
                ExceptionHelper.throwBusinessException("找不到文件,请确认文件已上传！");
            }
        }
        return getImportTransform().importParse(file);
    }

    @Override
    default public void importSave(File file){
        ImpExpModel impExpModel =  importParse(file);
        importSave(impExpModel.getDatas());
    }

    default public void importSave(Collection<T> datas) {
        Class c = ParameterizedTypeHelper.getParameterizedType(getClass(), Model.class);
        ImportSave.importSave(datas,c);
    }

    default ImportTransform getImportTransform()
    {
        String cacheId = this.getClass().getName()+ Constants.IMPORTTRANSFORM_CACHE_SUFFIX;
        ImportTransform transform = CacheDataHelper.get(cacheId);
        if( transform == null )
        {
            synchronized (cacheId) {
                transform = CacheDataHelper.get(cacheId);
                if( transform == null ) {
                    Class c = ParameterizedTypeHelper.getParameterizedType(getClass(), Model.class);
                    transform = new XlsImportTransform(c);
                    CacheDataHelper.put(cacheId, transform);
                }
            }
        }
        return transform;
    }


}
