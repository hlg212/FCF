package  io.github.hlg212.fcf;

import  io.github.hlg212.fcf.model.ImpExpModel;
import  io.github.hlg212.fcf.model.PageQuery;
import  io.github.hlg212.fcf.model.Qco;
import  io.github.hlg212.fcf.model.basic.File;

/**
 * 导出基类接口
 * 注意,根据配置,会将文件上传到文件中心。
 * 如果返回的context 为null，文件就是存储在文件中心，需要根据路径，到文件中心下载
 * @author huangligui
 * @date 2020年5月30日
 */
public interface Export<Q extends  Qco,IEO extends ImpExpModel> {


    /**
     * 导出用的模板
     *
     *
     */
    public File getExportTemplate();

    /**
     * 导出当前数据
     *
     */
    public File exportPage(PageQuery<Q> pageQuery);


}
