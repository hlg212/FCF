package io.hlg212.fcf.api.rtp;

import io.hlg212.fcf.CurdClientApi;
import io.hlg212.fcf.api.Constants;
import io.hlg212.fcf.model.Qco;
import io.hlg212.fcf.model.rtp.ITopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@FeignClient(contextId = Constants.ApiContextId.TopicApi,name=Constants.APP_APIGATEWAY_RTP,path =Constants.APP_RTP_PATH,url =Constants.AppFeignUrl.APP_RTP)
@RequestMapping(Constants.ApiMapping.TopicApi)
@ConditionalOnExpression("false")
public interface TopicApi <T extends ITopic, Q extends Qco> extends CurdClientApi<T,Q> {

    @RequestMapping(value = "/bind", method = RequestMethod.GET)
    public void bind(@RequestParam("topicId") String topicId, @RequestParam("destId") String destId, @RequestParam("routeKey") String routeKey);

    @RequestMapping(value = "/unBind", method = RequestMethod.GET)
    public void unBind(@RequestParam("topicId") String topicId, @RequestParam("destId") String destId, @RequestParam("routeKey") String routeKey);

    @RequestMapping(value = "/getBinds", method = RequestMethod.GET)
    public Collection<T> getBinds(@RequestParam("topicId")String topicId,@RequestParam("appCode") String appCode,@RequestParam("type")  String type);

    @RequestMapping(value="/getFormBinds",method=RequestMethod.GET)
    public Collection<T> getFormBinds(@RequestParam("topicId")String topicId,@RequestParam("appCode") String appCode,@RequestParam("type") String type);
}
