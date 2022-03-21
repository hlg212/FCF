package io.hlg212.fcf.api;

public class Constants {

    //public final static String APP_BASIC_PATH = "${hlg.feign.system.name:system}";
    public final static String APP_BASIC_PATH = "${hlg.feign.basic.name:basic}";
    public final static String APP_TASK_PATH = "${hlg.feign.task.name:task}";
    public final static String APP_FILES_PATH = "${hlg.feign.files.name:files}";
    public final static String APP_CAS_PATH = "${hlg.feign.cas.name:cas}";
    public final static String APP_ADMIN_PATH = "${hlg.feign.admin.name:admin}";
    public final static String APP_RTP_PATH = "${hlg.feign.rtp.name:rtp}";
    public final static String APP_MQ_PATH = "${hlg.feign.mq.name:mq}";
    public final static String APP_DAM_PATH = "${hlg.feign.dam.name:dam}";


    public final static String APP_APPLICATION_NAME = "${spring.application.name:}";

    public final static String APP_APIGATEWAY_BASIC = "${hlg.feign."+ APP_BASIC_PATH + ".gateway:apiGateway}";
    public final static String APP_APIGATEWAY_TASK = "${hlg.feign."+ APP_TASK_PATH + ".gateway:apiGateway}";
    public final static String APP_APIGATEWAY_CAS = "${hlg.feign."+ APP_CAS_PATH + ".gateway:apiGateway}";
    public final static String APP_APIGATEWAY_FILES = "${hlg.feign."+ APP_FILES_PATH + ".gateway:apiGateway}";
    public final static String APP_APIGATEWAY_ADMIN = "${hlg.feign."+ APP_ADMIN_PATH + ".gateway:apiGateway}";
    public final static String APP_APIGATEWAY_RTP = "${hlg.feign."+ APP_RTP_PATH + ".gateway:apiGateway}";
    public final static String APP_APIGATEWAY_MQ = "${hlg.feign."+ APP_MQ_PATH + ".gateway:apiGateway}";
    public final static String APP_APIGATEWAY_DAM = "${hlg.feign."+ APP_DAM_PATH + ".gateway:apiGateway}";



    public final static String API_PREFIX = "/api";

    public static class AppFeignUrl
    {
       // public final static String APP_BASIC =  "${hlg.feign."+ APP_BASIC_PATH + ".url:}";
        public final static String APP_BASIC =  "${hlg.feign."+ APP_BASIC_PATH + ".url:}";
        public final static String APP_TASK =  "${hlg.feign."+ APP_TASK_PATH + ".url:}";
        public final static String APP_CAS =  "${hlg.feign."+ APP_CAS_PATH + ".url:}";
        public final static String APP_FILES =  "${hlg.feign."+ APP_FILES_PATH + ".url:}";
        public final static String APP_APPLICATION_URL = "${hlg.feign."+ APP_APPLICATION_NAME + ".url:}";
        public final static String APP_ADMIN =  "${hlg.feign."+ APP_ADMIN_PATH + ".url:}";
        public final static String APP_RTP =  "${hlg.feign."+ APP_RTP_PATH + ".url:}";
        public final static String APP_MQ =  "${hlg.feign."+ APP_MQ_PATH + ".url:}";

        public final static String APP_DAM =  "${hlg.feign."+ APP_DAM_PATH + ".url:}";

    }

    public static class ApiMapping
    {
        public final static String AppApi = API_PREFIX + "/app";
        public final static String AuthApi = API_PREFIX + "/auth";
        public final static String FileApi = API_PREFIX + "/files";
        public final static String ClientApi = API_PREFIX + "/client";
        public final static String DicApi = API_PREFIX + "/dic";
        public final static String UserApi = API_PREFIX + "/user";
        public final static String OAuthApi = "/oauth";

        public final static String AnonymousResApi = API_PREFIX +"/anonymousRes";

        public final static String LongRunningResApi = API_PREFIX +"/longRunningRes";
        
        public final static String RouteApi = API_PREFIX + "/route";

        public final static String TopicApi = API_PREFIX + "/topic";

        public final static String PartitionApi = API_PREFIX +"/partition";

        public final static String TaskApi = API_PREFIX + "/task";

        public final static String OnlineUserApi = API_PREFIX + "/onlineUser";

        public final static String LogoutApi = API_PREFIX + "/logout";

        public final static String BlackWhiteListApi = API_PREFIX + "/blackWhiteList";

        public final static String AutomateApi = API_PREFIX + "/automate";

        public final static String CacheApi = API_PREFIX + "/cache";

        public final static String PobmsApi = API_PREFIX + "/pobms";


        public final static String UserLockApi = API_PREFIX + "/userLock";

        public final static String OrgDataAuthorityApi = API_PREFIX + "/orgDataAuthority";


        public final static String DataAuthorityPropertyConditionApi = API_PREFIX + "/dataAuthorityPropertyConditionApi";
        public final static String DataAuthorityConfigSetApi = API_PREFIX + "/dataAuthorityConfigSet";

        public final static String DataAuthorityConfigSetInfoApi = API_PREFIX + "/dataAuthorityConfigSetInfoApi";

    }


    public static class ApiContextId
    {
        public final static String AppApi = "AppApi";
        public final static String AuthApi =   "AuthApi";
        public final static String FileApi =   "FileApi";
        public final static String ClientApi =   "ClientApi";
        public final static String DicApi =   "DicApi";
        public final static String UserApi = "UserApi";
        public final static String OAuthApi = "OAuthApi";
        public final static String CacheApi = "CacheApi";
        public final static String UserInfoApi = "UserInfoApi";

        public final static String AnonymousResApi =  "AnonymousResApi";

        public final static String LongRunningResApi =  "LongRunningResApi";
        
        public final static String RouteApi =   "RouteApi";

        public final static String TopicApi =   "TopicApi";

        public final static String PartitionApi =  "PartitionApi";

        public final static String TaskApi =   "TaskApi";

        public final static String ExchangeApi =   "ExchangeApi";
        public final static String QueueApi =   "QueueApi";

        public final static String OnlineUserApi =   "OnlineUserApi";
        public final static String LogoutApi =   "LogoutApi";

        public final static String BlackWhiteListApi =   "BlackWhiteListApi";

        public final static String AutomateApi =   "AutomateApi";

        public final static String PobmsApi =   "PobmsApi";

        public final static String AppInfoApi =   "AppInfoApi";

        public final static String UserLockApi =   "UserLockApi";

        public final static String OrgDataAuthorityApi =   "OrgDataAuthorityApi";

        public final static String DataAuthorityConfigSetApi =   "DataAuthorityConfigSetApi";
        public final static String DataAuthorityPropertyConditionApi =   "DataAuthorityPropertyConditionApi";

        public final static String DataAuthorityConfigSetInfoApi =   "DataAuthorityConfigSetInfoApi";


    }

}
