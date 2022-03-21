package io.hlg212.fcf.web.api.client;

import io.hlg212.fcf.api.Constants;
import io.hlg212.fcf.model.mq.Binding;
import io.hlg212.fcf.model.mq.IBinding;
import io.hlg212.fcf.model.mq.MqPageInfo;
import io.hlg212.fcf.model.mq.Queue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;


@Configuration
class QueueApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.QueueApi,name=Constants.APP_APIGATEWAY_MQ,url =Constants.AppFeignUrl.APP_MQ,configuration =MqApiConfig.class)
    public interface QueueApi extends io.hlg212.fcf.api.mq.QueueApi<Queue> {

        @RequestMapping(value="/api/queues/{virtualHost}?page={page}&page_size={page_size}&use_regex=true&pagination=true&name={name}",method=RequestMethod.GET)
        @Override
        public MqPageInfo<Queue> findPage(@PathVariable("name") String name, @PathVariable("page") int page, @PathVariable("page_size") int pageSize, @PathVariable("virtualHost") String virtualHost);

        @RequestMapping(value="/api/queues/{virtualHost}/{name}/bindings/source",method=RequestMethod.GET)
        @Override
        public Collection<Binding> getQueueBindsTo(@PathVariable("name") String name, @PathVariable("virtualHost") String virtualHost);

        @RequestMapping(value="/api/queues/{virtualHost}/{name}/bindings/destination",method=RequestMethod.GET)
        @Override
        public  Collection<Binding> getQueueBindsFrom(@PathVariable("name") String name, @PathVariable("virtualHost") String virtualHost);

    }
}
