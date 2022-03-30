package  io.github.hlg212.fcf.model.basic;

import lombok.Data;

import java.util.List;

/**
 * @author  huangligui
 * @create: 2018-11-14 10:14
 **/
@Data
public class Dict implements IDict {

    private String key;
    private String value;
    private String name;
    private String id;
    private String pid;
    private String appId;
    private List<Dict> children;

}
