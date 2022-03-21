package io.hlg212.fcf.web.api.client;

import io.hlg212.fcf.api.Constants;
import io.hlg212.fcf.model.Qco;
import io.hlg212.fcf.model.rtp.Topic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;


@Configuration
class TopicApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.TopicApi,name=Constants.APP_APIGATEWAY_RTP,path =Constants.APP_RTP_PATH,url =Constants.AppFeignUrl.APP_RTP)
    public interface TopicApi extends io.hlg212.fcf.api.rtp.TopicApi<Topic,Qco> {

        @RequestMapping(value = "/getBinds", method = RequestMethod.GET)
        public Collection<Topic> getBinds(@RequestParam("topicId")String topicId, @RequestParam("appCode") String appCode, @RequestParam("type")  String type);

        @RequestMapping(value="/getFormBinds",method=RequestMethod.GET)
        public Collection<Topic> getFormBinds(@RequestParam("topicId")String topicId,@RequestParam("appCode") String appCode,@RequestParam("type") String type);
    }
}
