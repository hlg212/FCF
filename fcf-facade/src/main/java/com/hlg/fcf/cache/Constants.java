
package com.hlg.fcf.cache;

/**
 * @program: frame-parent
 * @description: 缓存相关常量
 * @author: huangligui
 * @create: 2019-03-01 09:49
 **/
public class Constants {





  private static final String PREFIX = "frame_";

  public static final String Dic =  PREFIX + "dic";
  public static final String Auth = PREFIX + "auth";
  public static final String App = PREFIX + "app";
  public static final String Route = PREFIX + "route";
  public static final String AnonymousRes = PREFIX + "anonymousRes";
  public static final String UserInfo = PREFIX + "userInfo";
  public static final String User = PREFIX + "user";
  public static final String OnlineUser =  PREFIX + "onlineUser";
  public static final String Token =  PREFIX + "token";
  public static final String Client = PREFIX + "client";
  public static final String BlackWhiteList = PREFIX + "blackWhiteList";
  public static final String FworkHelper =  PREFIX + "fworkHelper";
  public static final String LongRunningRes = PREFIX + "longRunningRes";


  public static class DicKey
  {
    public static final String getAllDics_prefix = "getAllDics:";
    public static final String getAllDics_spel = "'getAllDics:'+#p0";
  }

  public static class RouteKey
  {
    public static final String getAllRoutes_prefix = "getAllRoutes";
    public static final String getAllRoutes_spel = "'getAllRoutes'";
  }


  public static class AnonymousResKey
  {
    public static final String getAllUrls_prefix = "getAllUrls";
    public static final String getAllUrls_spel = "'getAllUrls'";
  }

  public static class ClientKey
  {
    public static final String getAuthoritysByKhdid_prefix = "getAuthoritysByKhdid";
    public static final String getAuthoritysByKhdid_spel = "'getAuthoritysByKhdid:'+ #p0 ";
  }

  public static class LongRunningResKey
  {
    public static final String getAllLongRunningRes_prefix = "getAllLongRunningRes";
    public static final String getAllLongRunningRes_spel = "'getAllLongRunningRes'";

    public static final String getUriTimeout_prefix = "getUriTimeout";
    public static final String getUriTimeout_spel = "'getUriTimeout:'+#p0";

  }

  public static class AuthKey
  {
    public static final String getAllAuthRes_spel = "'getAllAuthRes'";
    public static final String getAnonymousUrls_spel = "'getAnonymousUrls'";


    public static final String getAuthoritysByUserId_spel = "'getAuthoritysByUserId:'+ #p0 + ':' + #p1";

    public static final String getAllAuthoritysByUserId_spel = "'getAllAuthoritysByUserId:'+  #p0";

    public static final String getMenuByUserId_spel = "'getMenuByUserId:'+ #p0 + ':' + #p1";

    public static final String getAppsByUserId_spel = "'getAppsByUserId:' + #p0  + ':'   + #p1";


  }


  public static class UserKey
  {
    public static final String getUserByAccount_prefix = "getUserByAccount";
    public static final String getUserByAccount_spel = "'getUserByAccount:'+#p0";
  }

  public static class AppKey
  {
    public static final String getAppByCode_prefix = "getAppByCode";
    public static final String getAppByCode_spel = "'getAppByCode:'+#p0";
  }

  public static class BlackWhiteListKey
  {
    public static final String getBlackWhiteList_prefix = "getBlackWhiteList";
    public static final String getBlackWhiteList_spel = "'getBlackWhiteList'";

    public static final String getBlackWhiteListByAppCode_prefix = "getBlackWhiteListByAppCode";
    public static final String getBlackWhiteListByAppCode_spel = "'getBlackWhiteListByAppCode:'+ #p0";

    public static final String getBlackWhiteListByAccount_prefix = "getBlackWhiteListByAccount";
    public static final String getBlackWhiteListByAccount_spel = "'getBlackWhiteListByAccount:'+ #p0";


  }



  public static class CacheManager
  {
    public static final String SimpleCacheManager = "simpleCacheManager";

    public final static String DefCacheManager = "defCacheManager";

    public static final String ThreadLocalCacheManager = "threadLocalCacheManager";
  }

}
