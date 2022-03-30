package  io.github.hlg212.fcf.model.basic;

import  io.github.hlg212.fcf.model.Model;
import lombok.Data;

@Data
public class Client extends Model implements  IClient {

    private String id;
    private String autoApproveScopes;
    private String scopes;
    private String secret;
    private String account;
    private String registeredRedirectUri;
}
