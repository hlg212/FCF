package  io.github.hlg212.fcf.api;

import  io.github.hlg212.fcf.annotation.CacheableReadOnly;
import  io.github.hlg212.fcf.model.basic.IApp;
import  io.github.hlg212.fcf.model.basic.IRes;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(contextId = Constants.ApiContextId.AuthApi,name=Constants.APP_APIGATEWAY_BASIC,path =Constants.APP_BASIC_PATH,url =Constants.AppFeignUrl.APP_BASIC)
@RequestMapping(Constants.ApiMapping.AuthApi)
@ConditionalOnExpression("false")
public interface AuthApi {

    /**
     *  获得用户的权限列表
     * @param userId
     *  
     */
    @RequestMapping(value="/getAuthoritysByUserId",method=RequestMethod.GET)
    @CacheableReadOnly(key =  io.github.hlg212.fcf.cache.Constants.AuthKey.getAuthoritysByUserId_spel)
    public List<String> getAuthoritysByUserId(@RequestParam("appCode") String appCode, @RequestParam("userId") String userId);


    /**
     *  获得用户的权限列表
     * @param userId
     *  
     */
    @RequestMapping(value="/getAllAuthoritysByUserId",method=RequestMethod.GET)
    @CacheableReadOnly(key =  io.github.hlg212.fcf.cache.Constants.AuthKey.getAllAuthoritysByUserId_spel)
    public List<String> getAllAuthoritysByUserId( @RequestParam("userId") String userId);


    /**
     *  获得权限资源列表
     * @param userId
     *  
     */
    @RequestMapping(value="/getMenuByUserId",method=RequestMethod.GET)
    @CacheableReadOnly(key =  io.github.hlg212.fcf.cache.Constants.AuthKey.getMenuByUserId_spel)
    public <E extends  IRes>  List<E> getMenuByUserId(@RequestParam("appCode") String appCode, @RequestParam("userId") String userId);

    /**
     * 获得所有需要授权的资源
     */
    @RequestMapping(value="/getAllAuthRes",method=RequestMethod.GET)
    @CacheableReadOnly(key =  io.github.hlg212.fcf.cache.Constants.AuthKey.getAllAuthRes_spel)
    public <E extends  IRes>  List<E> getAllAuthRes();


    /**
     *  获得所有的匿名路径
     */
    @RequestMapping(value="/getAnonymousUrls",method=RequestMethod.GET)
    @CacheableReadOnly(key =  io.github.hlg212.fcf.cache.Constants.AuthKey.getAnonymousUrls_spel)
    public List<String> getAnonymousUrls();

    /**
     *  获取用户应用列表
     */
    @RequestMapping(value="/getAppsByUserId",method=RequestMethod.GET)
    @CacheableReadOnly(key =  io.github.hlg212.fcf.cache.Constants.AuthKey.getAppsByUserId_spel)
    public <E extends IApp> List<E> getAppsByUserId(@RequestParam("userId") String userId,@RequestParam(name="type",required = false) String type);
}
