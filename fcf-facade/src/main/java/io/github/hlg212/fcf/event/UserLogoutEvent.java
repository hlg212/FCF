
package  io.github.hlg212.fcf.event;

import  io.github.hlg212.fcf.annotation.RemoteEventAnnotation;
import lombok.Data;

@RemoteEventAnnotation(topic = Constants.Topic.FRAME )
@Data
public class UserLogoutEvent extends RemoteEvent {

    public static final String TYPE_LOGOUT = "logout";
    public static final String TYPE_KILL = "kill";
    public static final String TYPE_INVALID = "invalid";

    private String userId;
    private String userName;
    private String appCode;
    private String type;
    private String clientId;
    private String token;
    private String ip;
}