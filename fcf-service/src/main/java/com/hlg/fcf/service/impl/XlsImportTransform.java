package com.hlg.fcf.service.impl;

import com.hlg.fcf.ISerializable;
import com.hlg.fcf.model.ImpExpEnv;
import com.hlg.fcf.model.ImpExpModel;
import com.hlg.fcf.model.basic.File;
import com.hlg.fcf.util.ExceptionHelper;
import com.hlg.fcf.util.ImpExpHelper;
import com.hlg.fcf.util.StreamHelper;
import com.hlg.fcf.util.XlsHelper;
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
    protected void read(File file, ImpExpModel impExpModel) {
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
