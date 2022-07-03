package  io.github.hlg212.fcf.service;

import  io.github.hlg212.fcf.ISerializable;

/**
 * 新扩展的 增、修改、查询、珊瑚、导入、导出 接口
 * 该种扩展模式不友好，后期争取优化
 *
 * @author huangligui
 * @date 2020年5月30日
 */
public interface CurdieService<T extends ISerializable> extends CurdService<T>,ExportService<T>,ImportService<T>{

	
}
