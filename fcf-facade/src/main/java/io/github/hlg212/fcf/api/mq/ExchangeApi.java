package  io.github.hlg212.fcf.api.mq;

import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.PageInfo;
import  io.github.hlg212.fcf.model.basic.IRes;
import  io.github.hlg212.fcf.model.mq.Exchange;
import  io.github.hlg212.fcf.model.mq.IBinding;
import  io.github.hlg212.fcf.model.mq.IExchange;
import  io.github.hlg212.fcf.model.mq.MqPageInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.HashMap;

@FeignClient(contextId = Constants.ApiContextId.ExchangeApi,name=Constants.APP_APIGATEWAY_MQ,url =Constants.AppFeignUrl.APP_MQ)
@ConditionalOnExpression("false")
public interface ExchangeApi<T extends  IExchange>{

    @RequestMapping(value="/api/exchanges/{virtualHost}?page={page}&page_size={page_size}&use_regex=true&pagination=true&name={name}",method=RequestMethod.GET)
    public <P extends  PageInfo<T>> P findPage(@PathVariable("name")String name, @PathVariable("page")int page, @PathVariable("page_size")int pageSize, @PathVariable("virtualHost")String virtualHost);

    @RequestMapping(value="/api/exchanges/{virtualHost}/{name}/bindings/source",method=RequestMethod.GET)
    public  <E extends IBinding> Collection<E> getExchangeBindsTo(@PathVariable("name") String name, @PathVariable("virtualHost") String virtualHost);

    @RequestMapping(value="/api/exchanges/{virtualHost}/{name}/bindings/destination",method=RequestMethod.GET)
    public  <E extends  IBinding>  Collection<E> getExchangeBindsFrom(@PathVariable("name") String name,@PathVariable("virtualHost") String virtualHost);

}
