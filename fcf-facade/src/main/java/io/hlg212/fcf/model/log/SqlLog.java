package io.hlg212.fcf.model.log;

import lombok.Data;

import java.util.Date;

@Data
public class SqlLog implements ISqlLog {

    private static final long serialVersionUID = 3015056298206911311L;


    //事务id
    private String transactionId;

    private String sql;

    //创建时间
    private Date createTime;


}
