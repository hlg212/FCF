package io.hlg212.fcf.model.rtp;

import lombok.Data;

/**
 * @program: frame-parent
 * @description:  订阅者
 * @author: huangligui
 * @create: 2019-01-23 16:35
 **/
@Data
public class Subscriber implements  ISubscriber{

    private String id;
    //名称
    private String name;

    // B: 前端 E: 后台模块
    private String type;



}
