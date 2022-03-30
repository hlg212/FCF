package  io.github.hlg212.fcf.model;

import  io.github.hlg212.fcf.ISerializable;
import lombok.Data;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ImpExpModel<T extends  ISerializable> implements ISerializable {

    // 表头
    private String headers[] = null;

    // 属性，与表头对应
    private String props[] = null;
    //必填项，导出时会标红
    private String requiredProps[] = null;

    private Map dictProps = null;

    // 文件名称
    private String fileName = "导入导出.xlsx";

    // 标题
    private String title = "导出数据";

    // 导出数据
    private Collection<T> datas;

    private ImpExpEnv env;

    private boolean isWarp;

    // 数据对应的实体类
    private Class entityClass;

    private String exportTemplate;

    private String importTemplate;

}
