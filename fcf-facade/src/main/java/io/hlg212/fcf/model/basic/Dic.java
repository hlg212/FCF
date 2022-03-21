package io.hlg212.fcf.model.basic;

import lombok.Data;

import java.util.List;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2018-11-14 10:14
 **/
@Data
public class Dic implements IDic {

    private String key;
    private String value;
    private String mc;
    private String id;
    private String pid;
    private String appId;
    private List<Dic> children;

}
