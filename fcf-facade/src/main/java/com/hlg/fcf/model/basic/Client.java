package com.hlg.fcf.model.basic;

import com.hlg.fcf.model.Model;
import lombok.Data;

@Data
public class Client extends Model implements  IClient {

    private String id;
    private String autoApproveScopes;
    private String scopes;
    private String secret;
    private String name;
    private String registeredRedirectUri;
}
