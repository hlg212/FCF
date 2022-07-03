package  io.github.hlg212.fcf.api.mq;

import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.PageInfo;
import  io.github.hlg212.fcf.model.mq.IBinding;
import  io.github.hlg212.fcf.model.mq.IQueue;
import  io.github.hlg212.fcf.model.mq.QueueDeleteParam;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@FeignClient(contextId = Constants.ApiContextId.QueueApi,name=Constants.ApiName.QueueApi,url =Constants.ApiUrl.QueueApi)
@ConditionalOnExpression("false")
public interface QueueApi<T extends IQueue> {

    @RequestMapping(value="/api/queues/{virtualHost}?page={page}&page_size={page_size}&use_regex=true&pagination=true&name={name}",method=RequestMethod.GET)
    public <P extends PageInfo<T>> P findPage(@PathVariable("name") String name, @PathVariable("page") int page, @PathVariable("page_size") int pageSize, @PathVariable("virtualHost") String virtualHost);

    @RequestMapping(value="/api/queues/{virtualHost}/{name}",method=RequestMethod.DELETE)
    public void del(@PathVariable("name") String name, @PathVariable("virtualHost") String virtualHost, @RequestBody QueueDeleteParam param);

    @RequestMapping(value="/api/queues/{virtualHost}/{name}/bindings",method=RequestMethod.GET)
    public <E extends  IBinding> Collection<E> getBinds(@PathVariable("name") String name, @PathVariable("virtualHost") String virtualHost);
}
