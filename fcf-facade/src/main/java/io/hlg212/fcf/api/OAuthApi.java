package io.hlg212.fcf.api;

import io.hlg212.fcf.model.oauth.OAuthResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * 调用原始接口，认证失败时，消息会使用异常抛出
 * 使用  OAuthApiHelper 不会抛出异常
 * @author huangligui
 * @date 2019年1月11日
 */
@FeignClient(contextId = Constants.ApiContextId.OAuthApi, name = Constants.APP_APIGATEWAY_CAS, path = Constants.APP_CAS_PATH, url = Constants.AppFeignUrl.APP_CAS)
@RequestMapping(Constants.ApiMapping.OAuthApi)
@ConditionalOnExpression("false")
public interface OAuthApi {

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public OAuthResult login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("grant_type") String grantType, @RequestParam("appCode") String appCode);

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public OAuthResult codeLogin(@RequestParam("redirect_uri") String redirectUri, @RequestParam("code") String code, @RequestParam("grant_type") String grantType, @RequestParam("appCode") String appCode);

    @RequestMapping(value = "/check_token", method = RequestMethod.POST)
    public OAuthResult checkToken(@RequestParam("token") String token);


    @RequestMapping(value = "/token?grant_type=client_credentials", method = RequestMethod.POST)
    public OAuthResult clientCredentials();

    @RequestMapping(value = "/token?grant_type=account", method = RequestMethod.GET)
    public OAuthResult accountLogin(@RequestParam("account") String account,@RequestParam("appCode") String appCode);


}
