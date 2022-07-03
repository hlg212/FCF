
package  io.github.hlg212.fcf;

public class Constants {

  public static  final String FRAME_BASE_PACKAGE = "io.github.hlg212.fcf";

  public static  final String FRAME_NAME = "FCF";

  public static  final String FRAME_BEAN_PREFIX = "FCF.";


  public static class QueryClientApi
  {
    public static final String PAGE_NUM =  io.github.hlg212.fcf.model.Constants.QueryParam.PAGE_NUM + "";
    public static final String PAGE_SIZE =  io.github.hlg212.fcf.model.Constants.QueryParam.PAGE_SIZE + "";
  }


  public static class DataOperation
  {
    public static final String ADD = "ADD";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    public static final String QUERY = "QUERY";
  }
}
