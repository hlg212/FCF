package com.hlg.fcf.model.log;

import java.util.Date;

public class AuditLog {

    private static final long serialVersionUID = 3015056298202911471L;

    private String id;

    //表名
    private String table;

    //事务id
    private String transactionId;

    // 数据代码，应该是整个表唯一。 没有就使用ID
    private String billCode;

    // insert,update,del
    private String operationName;

    //老数据
    private String oldData;

    //新数据
    private String newData;

    //差异数据，变动数据
    private String diffData;

    //创建时间
    private Date createTime;

    // 用户
    private String userId;

    //用户中文名，冗余
    private String userName;
}
