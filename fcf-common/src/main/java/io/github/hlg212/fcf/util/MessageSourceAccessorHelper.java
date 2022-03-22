package  io.github.hlg212.fcf.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageSourceAccessorHelper {

    private static MessageSourceAccessorHelper _instance;

    @Autowired
    private MessageSource messages;

    private MessageSourceAccessor messageSourceAccessor;

    private static MessageSourceAccessorHelper getInstance()
    {
        if( _instance == null )
        {
            _instance = SpringHelper.getBean(MessageSourceAccessorHelper.class);
            _instance.messageSourceAccessor = new MessageSourceAccessor(_instance.messages,Locale.SIMPLIFIED_CHINESE);
        }
        return _instance;
    }

    public static String getMessage(String code)
    {
       return getInstance().messageSourceAccessor.getMessage(code);
    }

    public static String getMessage(String code,Object args[])
    {
        return getInstance().messageSourceAccessor.getMessage(code,args);
    }
}
