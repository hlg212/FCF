package com.hlg.fcf.service.impl;

import com.hlg.fcf.ISerializable;
import com.hlg.fcf.model.ImpExpEnv;
import com.hlg.fcf.model.ImpExpModel;
import com.hlg.fcf.model.basic.File;
import com.hlg.fcf.util.DicUtils;
import com.hlg.fcf.util.ExceptionHelper;
import com.hlg.fcf.util.FworkHelper;
import com.hlg.fcf.util.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 导出数据转换接口
 *
 * @author huangligui
 * @date 2020年8月18日
 */
@Slf4j
public abstract class ExportTransform<T extends ISerializable> {

    private Class<T> modelClass = null;

    public ExportTransform(Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    public File export(List datas) {
        ImpExpModel ieo = getImpExpModel();
        initExport(ieo);
        Collection result = exportTransform(datas);
        ieo.setDatas(result);
        return exportFile(ieo);
    }

    protected abstract File exportFile(ImpExpModel ieo);

    private void initExport(ImpExpModel ieo) {
        ImpExpEnv expEnv = new ImpExpEnv();
        expEnv.setExportUser(FworkHelper.geUserName());
        expEnv.setExportDateTime(new Date());
        ieo.setEnv(expEnv);
    }
    protected ImpExpModel getImpExpModel() {
        return ImpExpModelHelper.getImpExpModel(this.modelClass);
    }

    protected Object exportTransform(Object data) {
        ImpExpModel ieo = getImpExpModel();
        Object o = JsonHelper.toJsonObject(data);

        Map m = ieo.getDicProps();
        if (m != null) {
            Set<Map.Entry> set = m.entrySet();
            for (Map.Entry entry : set) {
                try {
                    Object value = PropertyUtils.getProperty(o, entry.getKey().toString());
                    if (value != null) {
                        String dicName = getDicName(entry.getValue().toString(), value.toString());
                        PropertyUtils.setProperty(o, entry.getKey().toString(), dicName);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    log.warn("转换数据字典[{}]失败", entry.getKey().toString(), e);
                }
            }
        }
        return o;
    }

    protected Collection exportTransform(Collection datas) {
        Collection result = new ArrayList(datas.size());
        datas.forEach(data -> {
            result.add(exportTransform(data));
        });

        return result;
    }

    private String getDicName(String dicStr, String value) {
        String dicKey = ImpExpModelHelper.getDicKey(dicStr);
        String appCode = ImpExpModelHelper.getDicApp(dicStr);
        if (StringUtils.isNotEmpty(dicKey)) {
            return DicUtils.getDicChildNameByVal(appCode, dicKey, value);
        }
        ExceptionHelper.throwBusinessException(String.format("找不到映射的字典[{}]!",dicStr));
        return null;
    }
}
