package io.github.hlg212.fcf.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import io.github.hlg212.fcf.properties.LogBackProperties;
import io.github.hlg212.fcf.util.SpringHelper;
import org.apache.skywalking.apm.toolkit.log.logback.v1.x.mdc.TraceIdMDCPatternLogbackLayout;
import org.slf4j.ILoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;

import java.security.CodeSource;
import java.security.ProtectionDomain;


public class LoggingCusApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {


   private static LogBackProperties logBackProperties;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        if( applicationContext.getParent() != null )
        {
            LogBackProperties properties = getLogBackProperties();
            if( properties == null || !properties.getEnable() )
            {
               return;
            }

            LoggerContext context = getLoggerContext();
            LogbackConfigurator configurator = new LogbackConfigurator(context);
            Appender<ILoggingEvent> appender =  grpcAppender(configurator);
            if( appender != null )
            {
                configurator.root(Level.INFO,appender);
            }

        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 11;
    }

    private LogBackProperties getLogBackProperties()
    {
        if( logBackProperties == null )
        {
            logBackProperties = SpringHelper.getBean(LogBackProperties.class);
        }
        return logBackProperties;
    }

    private Appender<ILoggingEvent> grpcAppender(LogbackConfigurator configurator) {

        LogBackProperties properties = getLogBackProperties();
        if( properties == null )
        {
            return null;
        }
        SwGRPCLogClientAppender appender = new SwGRPCLogClientAppender();
        LayoutWrappingEncoder encoder = new LayoutWrappingEncoder();

        TraceIdMDCPatternLogbackLayout layout = new TraceIdMDCPatternLogbackLayout();
        //layout.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{tid}] [%thread] %-5level %logger{36} -%msg%n");
        layout.setPattern(getLogBackProperties().getSwrpcPattern());

        encoder.setLayout(layout);
        encoder.setParent(appender);
        appender.setEncoder(encoder);

        configurator.start(encoder);
        configurator.start(layout);
        configurator.appender("grpc-log", appender);
        return appender;
    }

    private LoggerContext getLoggerContext() {
        ILoggerFactory factory = StaticLoggerBinder.getSingleton().getLoggerFactory();
        Assert.isInstanceOf(LoggerContext.class, factory,
                String.format(
                        "LoggerFactory is not a Logback LoggerContext but Logback is on "
                                + "the classpath. Either remove Logback or the competing "
                                + "implementation (%s loaded from %s). If you are using "
                                + "WebLogic you will need to add 'org.slf4j' to "
                                + "prefer-application-packages in WEB-INF/weblogic.xml",
                        factory.getClass(), getLocation(factory)));
        return (LoggerContext) factory;
    }

    private Object getLocation(ILoggerFactory factory) {
        try {
            ProtectionDomain protectionDomain = factory.getClass().getProtectionDomain();
            CodeSource codeSource = protectionDomain.getCodeSource();
            if (codeSource != null) {
                return codeSource.getLocation();
            }
        }
        catch (SecurityException ex) {
            // Unable to determine location
        }
        return "unknown location";
    }

}
