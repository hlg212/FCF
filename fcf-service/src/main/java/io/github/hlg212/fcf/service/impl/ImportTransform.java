package  io.github.hlg212.fcf.service.impl;

import  io.github.hlg212.fcf.ISerializable;
import  io.github.hlg212.fcf.model.ImpExpModel;
import  io.github.hlg212.fcf.model.basic.File;
import  io.github.hlg212.fcf.util.DicHelper;
import  io.github.hlg212.fcf.util.ExceptionHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static  io.github.hlg212.fcf.service.impl.ImpExpModelHelper.getImpExpModel;

/**
 * 导入格式转换
 *
 * @author huangligui
 * @date 2020年8月18日
 */
@Slf4j
public abstract class ImportTransform<T extends ISerializable> {

    private Class<T> modelClass = null;

    public ImportTransform(Class<T> modelClass) {
        this.modelClass = modelClass;
    }


    public ImpExpModel importParse(File file) {
        ImpExpModel impExpModel = getImpExpModel(modelClass);
        read(file,impExpModel);
        impExpModel.setDatas(importTransform(impExpModel.getDatas()));
        return impExpModel;
    }

    protected abstract void read(File file,ImpExpModel impExpModel);

    protected Collection importTransform(Collection datas) {
        Collection result = new ArrayList(datas.size());
        datas.forEach(data -> {
            result.add(importTransform(data));
        });

        return result;
    }

    protected Object importTransform(Object data) {
        ImpExpModel ieo = getImpExpModel(modelClass);

        Map m = ieo.getDicProps();
        if (m != null) {
            Set<Map.Entry> set = m.entrySet();
            for (Map.Entry entry : set) {
                try {
                    Object value = PropertyUtils.getProperty(data, entry.getKey().toString());
                    if (value != null) {
                        String dicValue = getDicValue(entry.getValue().toString(), value.toString());
                        PropertyUtils.setProperty(data, entry.getKey().toString(), dicValue);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    log.warn("转换数据字典[{}]失败", entry.getKey().toString(), e);
                }
            }
        }
        return data;
    }

    private String getDicValue(String dicStr, String value) {
        String dicKey = ImpExpModelHelper.getDicKey(dicStr);
        String appCode = ImpExpModelHelper.getDicApp(dicStr);
        if (StringUtils.isNotEmpty(dicKey)) {
            return DicHelper.getDicChildValByName(appCode, dicKey, value);
        }
        ExceptionHelper.throwBusinessException(String.format("找不到映射的字典[{}]!",dicStr));
        return null;
    }
}
