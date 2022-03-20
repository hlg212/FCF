
package com.hlg.fcf.event;


public class Constants {

  public static final String SPRING_BUS="springCloudEventBus";

  public static final  String EVENT_BUS = "eventBus";


  public static class Topic
  {
    //框架
    public static final String FRAME = "frame";

    // rtp
    public static final String RTP = "rtp";

    // cache
    public static final String CACHE = "cache";

    // 访问日志
    public static final String ACCESSLOG = "accessLog";

    // 事务日志
    public static final String TRANSACTIONLOG = "transactionLog";

    // 事务sql日志
    public static final String TRANSACTIONSQLLOG = "transactionSqlLog";
  }
}
