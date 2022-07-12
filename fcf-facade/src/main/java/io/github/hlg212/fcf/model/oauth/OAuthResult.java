package  io.github.hlg212.fcf.model.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthResult implements  IOAuthResult{

    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private String error;   
    private String errorDescription;
	private String clientId;

    private String active;
    private String userName;
    private Object scope;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getUserName() {
        return userName;
    }
    @JsonProperty(value = "user_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    @JsonProperty(value = "accessToken")
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public String getErrorDescription() {
        return this.errorDescription;
    }

    @JsonProperty(value = "access_token")
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @JsonProperty(value = "error_description")
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
	
	@Override
    public String getClientId() {
        return clientId;
    }
	 @JsonProperty(value = "client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Object getScope() {
        return scope;
    }

    public void setScope(Object scope) {
        this.scope = scope;
    }
}
