package  io.github.hlg212.fcf.service.impl;

import  io.github.hlg212.fcf.model.Model;

/**
 * 增删改查导入导出 整合适配器接口
 * 也可通过下列接口自由组合
 *
 * @see QueryServiceImpl
 * @see CurdServiceImpl
 * @see ImportServiceImpl
 * @see ExportServiceImpl
 * @author huangligui
 * @date 2020年8月18日
 */
public interface CurdieServiceImpl<T extends Model> extends CurdServiceImpl<T>,ImportServiceImpl<T>,ExportServiceImpl<T> {


}
