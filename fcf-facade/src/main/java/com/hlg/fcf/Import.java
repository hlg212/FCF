package com.hlg.fcf;

import com.hlg.fcf.model.ImpExpModel;
import com.hlg.fcf.model.basic.File;

/**
 * 导入基类接口
 * @author huangligui
 * @date 2020年5月30日
 */
public interface Import<T extends ISerializable,IEO extends ImpExpModel> {

    /**
     * 导入，然后直接保存
     *
     * @return
     */
    public void importSave(File file);

}
