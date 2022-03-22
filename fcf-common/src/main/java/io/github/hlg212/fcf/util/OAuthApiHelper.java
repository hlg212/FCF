package  io.github.hlg212.fcf.util;

import  io.github.hlg212.fcf.api.OAuthApi;
import  io.github.hlg212.fcf.model.oauth.OAuthResult;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OAuth 相关
 *
 * @author huangligui
 * @date 2020年5月30日
 */
@Component
public class OAuthApiHelper {


    private static OAuthApiHelper _instance;
    @Autowired
    private OAuthApi oAuthApi;


    private static OAuthApiHelper getInstance()
    {
        if( _instance == null )
        {
            _instance = SpringHelper.getBean(OAuthApiHelper.class);
        }
        return _instance;
    }



    public static OAuthResult login(String username, String password, String grantType,String appCode) {
        OAuthResult result = null;
        try {
            result = getInstance().oAuthApi.login(username, password, grantType,appCode);
        }catch (Exception e)
        {
            result = transform(e);
        }
        return result;

    }

    public static OAuthResult accountLogin(String account,String appCode) {
        OAuthResult result = null;
        try {
            result = getInstance().oAuthApi.accountLogin(account,appCode);
        }catch (Exception e)
        {
            result = transform(e);
        }
        return result;

    }


    public static OAuthResult codeLogin(String redirectUri, String code, String grantType,String appCode) {

        OAuthResult result = null;
        try {
            result = getInstance().oAuthApi.codeLogin(redirectUri, code, grantType,appCode);
        }catch (Exception e)
        {
            result = transform(e);
        }
        return result;
    }


    public static OAuthResult checkToken(String token) {
        OAuthResult result = null;
        try {
            result = getInstance().oAuthApi.checkToken(token);
        }catch (Exception e)
        {
            result = transform(e);
        }
        return result;
    }


    public static  OAuthResult clientCredentials() {
        OAuthResult result = null;
        try {
            result = getInstance().oAuthApi.clientCredentials();
        }catch (Exception e)
        {
            result = transform(e);
        }
        return result;
    }

    private static OAuthResult transform(Throwable throwable)
    {
        OAuthResult result = null;
        if( throwable instanceof FeignException)
        {
            FeignException fex = (FeignException)throwable;
            result = JsonHelper.parseObject(fex.contentUTF8(),OAuthResult.class);
        }
        else if( throwable instanceof Exception )
        {
            ExceptionHelper.throwServerException((Exception)throwable);
        }

        return result;
    }
}
