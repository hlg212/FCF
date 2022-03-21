package io.hlg212.fcf.web.util;

import io.hlg212.fcf.context.TokenContext;
import io.hlg212.fcf.util.ThreadLocalHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * @program: frame-parent
 * @description:  token 帮助类
 * @author: huangligui
 * @create: 2018-12-25 09:24
 **/
public class TokenHelper {

    public static final String BEARER = "bearer ";
    public static final String TOKEN_KEY = "Authorization";

    public static final String TOKEN_CONTEXT = "TOKEN_CONTEXT";

    public static String getToken()
    {
        TokenContext context = getContext();
        if( context != null )
        {
            return context.getToken();
        }
        return null;
    }

    public static String getBearerToken()
    {
        String token = getToken();
        if( StringUtils.isNotEmpty(token) )
        {
            if( token.startsWith(BEARER) )
            {
                return token;
            }
            else
            {
                return BEARER + token;
            }

        }
        return null;
    }

    public static void setContext(TokenContext context)
    {
        ThreadLocalHelper.set(TOKEN_CONTEXT, context);
    }

    public static TokenContext getContext()
    {
        return (TokenContext)ThreadLocalHelper.get(TOKEN_CONTEXT);
    }

}
