package io.github.hlg212.fcf.log;

import io.github.hlg212.fcf.util.AccessContextHelper;

public class SwTraceIdLoggingParam implements LoggingParam{
    @Override
    public String getKey() {
        return "SW_TRACE_ID";
    }

    @Override
    public String getValue() {
        return AccessContextHelper.getTraceId();
    }
}
