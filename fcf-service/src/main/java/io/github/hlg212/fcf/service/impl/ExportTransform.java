package  io.github.hlg212.fcf.service.impl;

import  io.github.hlg212.fcf.ISerializable;
import  io.github.hlg212.fcf.model.ImpExpEnv;
import  io.github.hlg212.fcf.model.ImpExpModel;
import  io.github.hlg212.fcf.model.basic.File;
import  io.github.hlg212.fcf.util.DictHelper;
import  io.github.hlg212.fcf.util.ExceptionHelper;
import  io.github.hlg212.fcf.util.FworkHelper;
import  io.github.hlg212.fcf.util.JsonHelper;
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

        Map m = ieo.getDictProps();
        if (m != null) {
            Set<Map.Entry> set = m.entrySet();
            for (Map.Entry entry : set) {
                try {
                    Object value = PropertyUtils.getProperty(o, entry.getKey().toString());
                    if (value != null) {
                        String dicName = getDictName(entry.getValue().toString(), value.toString());
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

    private String getDictName(String dictStr, String value) {
        String dictKey = ImpExpModelHelper.getDictKey(dictStr);
        String appCode = ImpExpModelHelper.getDictApp(dictStr);
        if (StringUtils.isNotEmpty(dictKey)) {
            return DictHelper.getDictChildNameByVal(appCode, dictKey, value);
        }
        ExceptionHelper.throwBusinessException(String.format("找不到映射的字典[{}]!",dictStr));
        return null;
    }
}
