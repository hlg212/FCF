package io.github.hlg212.fcf.log;


import io.github.hlg212.fcf.util.AccessContextHelper;

public class AccessIdLoggingParam implements LoggingParam{
    @Override
    public String getKey() {
        return "ACCESS_ID";
    }

    @Override
    public String getValue() {
        return AccessContextHelper.getAccessId();
    }
}
