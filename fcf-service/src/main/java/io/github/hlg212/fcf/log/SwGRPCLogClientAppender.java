package io.github.hlg212.fcf.log;


import io.github.hlg212.fcf.properties.LogBackProperties;
import io.github.hlg212.fcf.util.SpringHelper;
import org.apache.skywalking.apm.toolkit.log.logback.v1.x.log.GRPCLogClientAppender;

public class SwGRPCLogClientAppender extends GRPCLogClientAppender {


    private static LogBackProperties logBackProperties = null;


    private static LogBackProperties getLogBackProperties()
    {
        if( logBackProperties == null )
        {
            logBackProperties = SpringHelper.getBean(LogBackProperties.class);
        }
        return logBackProperties;
    }

    private boolean isAppend()
    {
        LogBackProperties  p = getLogBackProperties();
        if( p != null )
        {
            return p.getEnableSwgrpcLog();

        }
        return false;
    }

    @Override
    protected void subAppend(Object event) {

        if( isAppend() ) {
            super.subAppend(event);
        }
    }

}
