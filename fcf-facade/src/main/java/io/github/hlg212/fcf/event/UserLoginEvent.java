
package  io.github.hlg212.fcf.event;

import  io.github.hlg212.fcf.annotation.RemoteEventAnnotation;
import lombok.Data;

@RemoteEventAnnotation(topic = Constants.Topic.FRAME)
@Data
public class UserLoginEvent extends  RemoteEvent {

    //用户输入账号密码，登录认证中心
    public static final String LOGINTYPE_INTERACTIVE = "interactive";
    //用户授权给第三方，token认证
    public static final String LOGINTYPE_CLIENT = "client";

    private String userId;
    private String clientId;
    private String userName;
    private String appCode;
    private String token;
    private String loginType;
    private String ip;

}