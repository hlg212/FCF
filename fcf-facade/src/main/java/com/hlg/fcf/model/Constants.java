
package com.hlg.fcf.model;

/**
 * @program: frame-parent
 * @description: 模型相关常量
 * @author: huangligui
 * @create: 2019-03-01 09:49
 **/
public class Constants {

  public static final String BOOLEAN_Y = "Y";

  public static final String BOOLEAN_N = "N";



  public static class QueryParam
  {
      public static final int PAGE_NUM = 1;
      public static final int PAGE_SIZE = 10;
  }

  public static class PageQuery
  {
    public static final int PAGE_NUM = QueryParam.PAGE_NUM;
    public static final int PAGE_SIZE = QueryParam.PAGE_SIZE;
  }
}
