package  io.github.hlg212.fcf.api;


public class Constants {
    public final static String APP_APPLICATION_NAME = "${spring.application.name:}";

    public final static String API_PREFIX = "/api";


    public static class ApiUrl {
        public final static String AppApi = "${fcf.feign.AppApi.url:}";
        public final static String AuthApi = "${fcf.feign.AuthApi.url:}";
        public final static String FileApi = "${fcf.feign.FileApi.url:}";
        public final static String ClientApi = "${fcf.feign.ClientApi.url:}";
        public final static String DictApi = "${fcf.feign.DictApi.url:}";
        public final static String UserApi = "${fcf.feign.UserApi.url:}";
        public final static String UserInfoApi = "${fcf.feign.UserInfoApi.url:}";
        public final static String OAuthApi = "${fcf.feign.OAuthApi.url:}";
        public final static String AnonymousResApi = "${fcf.feign.AnonymousResApi.url:}";
        public final static String LongRunningResApi = "${fcf.feign.LongRunningResApi.url:}";
        public final static String RouteApi = "${fcf.feign.RouteApi.url:}";
        public final static String TopicApi = "${fcf.feign.TopicApi.url:}";
        public final static String PartitionApi = "${fcf.feign.PartitionApi.url:}";
        public final static String TaskApi = "${fcf.feign.TaskApi.url:}";

        public final static String ExchangeApi =   "${fcf.feign.ExchangeApi.url:}";
        public final static String QueueApi =   "${fcf.feign.QueueApi.url:}";


        public final static String OnlineUserApi = "${fcf.feign.OnlineUserApi.url:}";
        public final static String LogoutApi = "${fcf.feign.LogoutApi.url:}";
        public final static String BlackWhiteListApi = "${fcf.feign.BlackWhiteListApi.url:}";
        public final static String AutomateApi = "${fcf.feign.AutomateApi.url:}";
        public final static String CacheApi = "${fcf.feign.CacheApi.url:}";
        public final static String PoInfoApi = "${fcf.feign.PoInfoApi.url:}";
        public final static String AppInfoApi = "${fcf.feign.AppInfoApi.url:}";

        public final static String UserLockApi = "${fcf.feign.UserLockApi.url:}";
        public final static String OrgDataAuthorityApi = "${fcf.feign.OrgDataAuthorityApi.url:}";
        public final static String DataAuthorityPropertyConditionApi = "${fcf.feign.DataAuthorityPropertyConditionApi.url:}";
        public final static String DataAuthorityConfigSetApi = "${fcf.feign.DataAuthorityConfigSetApi.url:}";
        public final static String DataAuthorityConfigSetInfoApi = "${fcf.feign.DataAuthorityConfigSetInfoApi.url:}";

    }


    public static class ApiName
    {
        public final static String AppApi = "${fcf.feign.AppApi.name:apiGateway}";
        public final static String AuthApi = "${fcf.feign.AuthApi.name:apiGateway}";
        public final static String FileApi = "${fcf.feign.FileApi.name:apiGateway}";
        public final static String ClientApi = "${fcf.feign.ClientApi.name:apiGateway}";
        public final static String DictApi = "${fcf.feign.DictApi.name:apiGateway}";
        public final static String UserApi = "${fcf.feign.UserApi.name:apiGateway}";
        public final static String UserInfoApi = "${fcf.feign.UserInfoApi.name:apiGateway}";
        public final static String OAuthApi = "${fcf.feign.OAuthApi.name:apiGateway}";
        public final static String AnonymousResApi = "${fcf.feign.AnonymousResApi.name:apiGateway}";
        public final static String LongRunningResApi = "${fcf.feign.LongRunningResApi.name:apiGateway}";
        public final static String RouteApi = "${fcf.feign.RouteApi.name:apiGateway}";
        public final static String TopicApi = "${fcf.feign.TopicApi.name:apiGateway}";
        public final static String PartitionApi = "${fcf.feign.PartitionApi.name:apiGateway}";
        public final static String TaskApi = "${fcf.feign.TaskApi.name:apiGateway}";
        public final static String ExchangeApi =   "${fcf.feign.ExchangeApi.name:apiGateway}";
        public final static String QueueApi =   "${fcf.feign.QueueApi.name:apiGateway}";
        public final static String OnlineUserApi = "${fcf.feign.OnlineUserApi.name:apiGateway}";
        public final static String LogoutApi = "${fcf.feign.LogoutApi.name:apiGateway}";
        public final static String BlackWhiteListApi = "${fcf.feign.BlackWhiteListApi.name:apiGateway}";
        public final static String AutomateApi = "${fcf.feign.AutomateApi.name:apiGateway}";
        public final static String CacheApi = "${fcf.feign.CacheApi.name:apiGateway}";
        public final static String PoInfoApi = "${fcf.feign.PoInfoApi.name:apiGateway}";

        public final static String AppInfoApi = "${fcf.feign.AppInfoApi.name:apiGateway}";

        public final static String UserLockApi = "${fcf.feign.UserLockApi.name:apiGateway}";
        public final static String OrgDataAuthorityApi = "${fcf.feign.OrgDataAuthorityApi.name:apiGateway}";
        public final static String DataAuthorityPropertyConditionApi = "${fcf.feign.DataAuthorityPropertyConditionApi.name:apiGateway}";
        public final static String DataAuthorityConfigSetApi = "${fcf.feign.DataAuthorityConfigSetApi.name:apiGateway}";
        public final static String DataAuthorityConfigSetInfoApi = "${fcf.feign.DataAuthorityConfigSetInfoApi.name:apiGateway}";

    }


    public static class ApiPath
    {
        public final static String AppApi = "${fcf.feign.AppApi.path:basic}";
        public final static String AuthApi = "${fcf.feign.AuthApi.path:basic}";
        public final static String FileApi = "${fcf.feign.FileApi.path:file}";
        public final static String ClientApi = "${fcf.feign.ClientApi.path:basic}";
        public final static String DictApi = "${fcf.feign.DictApi.path:basic}";
        public final static String UserApi = "${fcf.feign.UserApi.path:basic}";
        public final static String UserInfoApi = "${fcf.feign.UserInfoApi.path:cas}";
        public final static String OAuthApi = "${fcf.feign.OAuthApi.path:cas}";
        public final static String AnonymousResApi = "${fcf.feign.AnonymousResApi.path:gatewayAdmin}";
        public final static String LongRunningResApi = "${fcf.feign.LongRunningResApi.path:gatewayAdmin}";
        public final static String RouteApi = "${fcf.feign.RouteApi.path:gatewayAdmin}";
        public final static String TopicApi = "${fcf.feign.TopicApi.path:apiGateway}";
        public final static String PartitionApi = "${fcf.feign.PartitionApi.path:task}";
        public final static String TaskApi = "${fcf.feign.TaskApi.path:task";

        public final static String OnlineUserApi = "${fcf.feign.OnlineUserApi.path:cas}";
        public final static String LogoutApi = "${fcf.feign.LogoutApi.path:cas}";
        public final static String BlackWhiteListApi = "${fcf.feign.BlackWhiteListApi.path:gatewayAdmin}";

        //public final static String AutomateApi = "${fcf.feign.AutomateApi.path:apiGateway}";

        public final static String CacheApi = "${fcf.feign.CacheApi.path:admin}";
       // public final static String PoInfoApi = "${fcf.feign.PoInfoApi.path:apiGateway}";
        public final static String UserLockApi = "${fcf.feign.UserLockApi.path:cas}";
        public final static String OrgDataAuthorityApi = "${fcf.feign.OrgDataAuthorityApi.path:dam}";
        public final static String DataAuthorityPropertyConditionApi = "${fcf.feign.DataAuthorityPropertyConditionApi.path:dam}";
        public final static String DataAuthorityConfigSetApi = "${fcf.feign.DataAuthorityConfigSetApi.path:dam}";
        public final static String DataAuthorityConfigSetInfoApi = "${fcf.feign.DataAuthorityConfigSetInfoApi.path:dam}";

    }

    public static class ApiMapping
    {
        public final static String AppApi = API_PREFIX + "/app";
        public final static String AuthApi = API_PREFIX + "/auth";
        public final static String FileApi = API_PREFIX + "/files";
        public final static String ClientApi = API_PREFIX + "/client";
        public final static String DictApi = API_PREFIX + "/dict";
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

        public final static String PoInfoApi = API_PREFIX + "/poInfo";

        public final static String AppInfoApi =  "/actuator";

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
        public final static String DictApi =   "DictApi";
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

        public final static String PoInfoApi =   "PoInfoApi";

        public final static String AppInfoApi =   "AppInfoApi";

        public final static String UserLockApi =   "UserLockApi";

        public final static String OrgDataAuthorityApi =   "OrgDataAuthorityApi";

        public final static String DataAuthorityConfigSetApi =   "DataAuthorityConfigSetApi";
        public final static String DataAuthorityPropertyConditionApi =   "DataAuthorityPropertyConditionApi";

        public final static String DataAuthorityConfigSetInfoApi =   "DataAuthorityConfigSetInfoApi";


    }

}
