package com.hlg.fcf.model.oauth;

public interface IOAuthResult {

    public String getAccessToken();

    public String getError();

    public String getErrorDescription();
	
	public String getClientId();
}
