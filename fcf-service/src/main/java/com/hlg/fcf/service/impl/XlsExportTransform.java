package com.hlg.fcf.service.impl;

import com.hlg.fcf.ISerializable;
import com.hlg.fcf.model.ImpExpModel;
import com.hlg.fcf.model.basic.File;
import com.hlg.fcf.util.ExceptionHelper;
import com.hlg.fcf.util.ImpExpHelper;
import com.hlg.fcf.util.XlsHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * excel格式转换
 *
 * @author huangligui
 * @date 2020年8月18日
 */
@Slf4j
//@Component
public class XlsExportTransform<T extends ISerializable> extends ExportTransform<T> {


    public XlsExportTransform(Class<T> modelClass) {
        super(modelClass);
    }

    @Override
    protected File exportFile(ImpExpModel ieo) {
        File file = new File();

        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                InputStream in = ImpExpHelper.getExportTemplate(ieo);
        ) {
            XlsHelper.export(in, byteArrayOutputStream, ieo);
            file.setContent(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            ExceptionHelper.throwServerException(e);
        }

        file.setFileName(ieo.getFileName());
        file.setContentType("xlsx");
        return file;
    }


}
