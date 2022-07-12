package  io.github.hlg212.fcf.api;

import io.github.hlg212.fcf.model.basic.IClient;
import io.github.hlg212.fcf.model.basic.IClientAuthority;
import io.github.hlg212.fcf.model.mq.IBinding;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@FeignClient(contextId = Constants.ApiContextId.ClientApi,name=Constants.ApiName.ClientApi,path =Constants.ApiPath.ClientApi,url =Constants.ApiUrl.ClientApi)
@RequestMapping(Constants.ApiMapping.ClientApi)
@ConditionalOnExpression("false")
public interface ClientApi<T extends IClient> {
    @RequestMapping(value="/getById",method= RequestMethod.GET)
    public T getById(@RequestParam("id") String id);

    @RequestMapping(value="/getAuthoritys",method=RequestMethod.GET)
    public <E extends IClientAuthority> Collection<E> getAuthoritys(@RequestParam("clientId") String clientId);
}
