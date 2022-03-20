package com.hlg.fcf.service;

import com.hlg.fcf.Export;
import com.hlg.fcf.ISerializable;
import com.hlg.fcf.model.ImpExpModel;
import com.hlg.fcf.model.Qco;
import com.hlg.fcf.model.basic.File;

import java.util.List;

/**
 * 导出Service接口
 * @author huangligui
 * @date 2020年5月30日
 */
public interface ExportService<T extends ISerializable>  extends Export<Qco,ImpExpModel>,FrameService {

    /**
     * 导出当前数据
     * @return
     */
    public File export(List datas );
}
