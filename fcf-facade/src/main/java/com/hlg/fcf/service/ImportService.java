package com.hlg.fcf.service;

import com.hlg.fcf.ISerializable;
import com.hlg.fcf.Import;
import com.hlg.fcf.model.ImpExpModel;
import com.hlg.fcf.model.basic.File;

import java.util.Collection;

/**
 * 导入Service接口
 * @author huangligui
 * @date 2020年5月30日
 */
public interface ImportService<T extends ISerializable>  extends Import<T,ImpExpModel>,FrameService {

    /**
     * 解析导入文件
     * 返回解析后的对象，一般为datas
     * @return
     */
    public ImpExpModel importParse(File file);

    public void importSave(Collection<T> datas);
}
