package  io.github.hlg212.fcf.service;

import  io.github.hlg212.fcf.Export;
import  io.github.hlg212.fcf.ISerializable;
import  io.github.hlg212.fcf.model.ImpExpModel;
import  io.github.hlg212.fcf.model.Qco;
import  io.github.hlg212.fcf.model.basic.File;

import java.util.List;

/**
 * 导出Service接口
 * @author huangligui
 * @date 2020年5月30日
 */
public interface ExportService<T extends ISerializable>  extends Export<Qco,ImpExpModel>,FrameService {

    /**
     * 导出当前数据
     *
     */
    public File export(List datas );
}
