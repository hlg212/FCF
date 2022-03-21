package io.hlg212.fcf.model.log;

import lombok.Data;

import java.util.Date;

@Data
public class AccessLog implements  IAccessLog {

    private static final long serialVersionUID = 3015056298206911471L;

    private String id;
    // 用户
    private String userId;

    //用户中文名，冗余
    private String userName;
    // 客户端浏览器地址
    private String clientIp;

    //接口地址
    private String uri;
    //请求地址
    private String requestUrl;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //耗时
    private Long duration;
    //应用
    private String appCode;

    //调用链跟踪ID
    private String traceId;
    //状态:成功、失败
    private String state;
    //请求参数
    private String requestParam;
    //异常信息
    private String exceptionMess;

    //异常堆栈
    private String exceptionStack;

    //请求头
    private String headers;


}
