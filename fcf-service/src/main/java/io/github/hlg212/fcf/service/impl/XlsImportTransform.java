package  io.github.hlg212.fcf.service.impl;

import  io.github.hlg212.fcf.ISerializable;
import  io.github.hlg212.fcf.model.ImpExpEnv;
import  io.github.hlg212.fcf.model.ImpExpModel;
import  io.github.hlg212.fcf.model.basic.File;
import io.github.hlg212.fcf.model.basic.IFile;
import  io.github.hlg212.fcf.util.ExceptionHelper;
import  io.github.hlg212.fcf.util.ImpExpHelper;
import  io.github.hlg212.fcf.util.StreamHelper;
import  io.github.hlg212.fcf.util.XlsHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * excel格式转换
 *
 * @author huangligui
 * @date 2020年8月18日
 */
@Slf4j
//@Component
public class XlsImportTransform<T extends ISerializable> extends ImportTransform<T> {
    public XlsImportTransform(Class<T> modelClass) {
        super(modelClass);
    }

    @Override
    protected void read(IFile file, ImpExpModel impExpModel) {
        InputStream templateInputStream = null;
        try {
            templateInputStream = ImpExpHelper.createXmlInputStream(impExpModel);
        } catch (Exception e) {
            log.error("创建导入模板失败!", e);
            ExceptionHelper.throwServerException("创建导入模板失败!", e);
        }

        InputStream inputStream = StreamHelper.byteToInputStream(file.getContent());
        read(inputStream, templateInputStream, impExpModel);
    }


    private void read(InputStream inputStream, InputStream template, ImpExpModel impExpModel) {
        Map data = new HashMap();
        data.put("datas", new ArrayList<>());
        data.put("env", new ImpExpEnv());
        try {
            XlsHelper.readData(inputStream, template, data);
        } catch (Exception e) {
            log.error("数据解析失败!", e);
            ExceptionHelper.throwServerException("数据解析失败!", e);
        }
        impExpModel.setDatas((Collection) data.get("datas"));
        impExpModel.setEnv((ImpExpEnv) data.get("env"));
    }
}
