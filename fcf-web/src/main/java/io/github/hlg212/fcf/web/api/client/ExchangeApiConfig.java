package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.mq.Binding;
import  io.github.hlg212.fcf.model.mq.Exchange;
import  io.github.hlg212.fcf.model.mq.MqPageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;


@Configuration
class ExchangeApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.ExchangeApi,name=Constants.ApiName.ExchangeApi,url =Constants.ApiUrl.ExchangeApi,configuration =MqApiConfig.class)
    public interface ExchangeApi extends  io.github.hlg212.fcf.api.mq.ExchangeApi<Exchange> {

        @RequestMapping(value="/api/exchanges/{virtualHost}?page={page}&page_size={page_size}&use_regex=true&pagination=true&name={name}",method=RequestMethod.GET)
        @Override
        public MqPageInfo<Exchange> findPage(@PathVariable("name")String name, @PathVariable("page")int page, @PathVariable("page_size")int pageSize, @PathVariable("virtualHost")String virtualHost);

        @RequestMapping(value="/api/exchanges/{virtualHost}/{name}/bindings/source",method=RequestMethod.GET)
        public Collection<Binding> getExchangeBindsTo(@PathVariable("name") String name, @PathVariable("virtualHost") String virtualHost);

        @RequestMapping(value="/api/exchanges/{virtualHost}/{name}/bindings/destination",method=RequestMethod.GET)
        public   Collection<Binding> getExchangeBindsFrom(@PathVariable("name") String name,@PathVariable("virtualHost") String virtualHost);

    }

}
