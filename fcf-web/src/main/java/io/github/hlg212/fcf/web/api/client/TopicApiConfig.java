package  io.github.hlg212.fcf.web.api.client;

import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.Qco;
import  io.github.hlg212.fcf.model.rtp.Topic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;


@Configuration
class TopicApiConfig {

    @FeignClient(contextId = Constants.ApiContextId.TopicApi,name=Constants.ApiName.TopicApi,path =Constants.ApiPath.TopicApi,url =Constants.AppFeignUrl.TopicApi)
    public interface TopicApi extends  io.github.hlg212.fcf.api.rtp.TopicApi<Topic,Qco> {

        @RequestMapping(value = "/getBinds", method = RequestMethod.GET)
        public Collection<Topic> getBinds(@RequestParam("topicId")String topicId, @RequestParam("appCode") String appCode, @RequestParam("type")  String type);

        @RequestMapping(value="/getFormBinds",method=RequestMethod.GET)
        public Collection<Topic> getFormBinds(@RequestParam("topicId")String topicId,@RequestParam("appCode") String appCode,@RequestParam("type") String type);
    }
}
