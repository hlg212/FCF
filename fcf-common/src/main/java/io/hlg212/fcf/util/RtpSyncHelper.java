package io.hlg212.fcf.util;

import io.hlg212.fcf.api.rtp.TopicApi;
import io.hlg212.fcf.model.rtp.ITopic;
import io.hlg212.fcf.model.rtp.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息推送帮助类
 * 该类提供的方法都是同步的方式
 *
 * @author: huangligui
 * @create: 2019-01-29 10:52
 **/
@Component
public class RtpSyncHelper {


    private static RtpSyncHelper _instance;

    @Autowired
    private TopicApi topicApi;

    private static final String TOPIC_SPLIT = ".";

    private static RtpSyncHelper getInstance(){
        if( _instance == null ){
            _instance = SpringHelper.getBean(RtpSyncHelper.class);
        }
        return _instance;

    }
    /**
     * 自定topic
     *
     * @param topic
     */
    public static void createTopic(ITopic topic)
    {
       getInstance().topicApi.save(topic);
    }

    /**
     * 创建话题，appCode为空代表是一个公共的话题
     * @param id
     * @param appCode
     */
    public static void createTopic(String id,String appCode)
    {
        ITopic topic = RtpHelper.TopicBuild.createTopic(id,Topic.TYPE_CUS,appCode);
        createTopic(topic);
    }

    /**
     *  创建话题，该话题默认属于本服务
     * @param id
     */
    public static void createTopic(String id)
    {
        createTopic(id,AppContextHelper.getAppCode());
    }

    public static void delTopic(String id)
    {
        getInstance().topicApi.deleteById(  RtpHelper.TopicBuild.generateTopicId(id,Topic.TYPE_CUS,AppContextHelper.getAppCode()) ) ;
    }

    /**
     *  话题之间绑定
     *  注意 该topicId 属于完整的全Id
     *  可以认为是 加入房间
     *
     * @param topicId
     * @param destId
     * @param routeKey
     */
    public static void bind(String topicId, String destId, String routeKey)
    {
        getInstance().topicApi.bind(topicId,destId,routeKey);
    }

    public static void unBind(String topicId, String destId,String routeKey)
    {
        getInstance().topicApi.unBind(topicId,destId,routeKey);
    }


}
