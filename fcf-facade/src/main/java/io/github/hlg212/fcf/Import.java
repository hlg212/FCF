package  io.github.hlg212.fcf;

import  io.github.hlg212.fcf.model.ImpExpModel;
import  io.github.hlg212.fcf.model.basic.File;

/**
 * 导入基类接口
 * @author huangligui
 * @date 2020年5月30日
 */
public interface Import<T extends ISerializable,IEO extends ImpExpModel> {

    /**
     * 导入，然后直接保存
     *
     *
     */
    public void importSave(File file);

}
