package io.hlg212.fcf.model.log;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionLog implements  ITransactionLog {

    private static final long serialVersionUID = 3015056118206911471L;

    private String id;

    // 用户
    private String userId;

    //用户中文名，冗余
    private String userName;

    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //耗时
    private Long duration;
    //操作名称，一般为类+方法名称
    private String operationName;

    //访问日志ID
    private String accessId;

    //应用
    private String appCode;

    //调用链跟踪ID
    private String traceId;
    //状态:成功、失败
    private String state;
    //参数 json
    private String params;
    //异常信息
    private String exceptionMess;
    private String exceptionStack;



}
