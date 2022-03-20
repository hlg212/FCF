package com.hlg.fcf.util;

import com.hlg.fcf.event.rtp.*;
import com.hlg.fcf.model.rtp.ITopic;
import com.hlg.fcf.model.rtp.Message;
import com.hlg.fcf.model.rtp.Topic;
import org.apache.commons.lang.StringUtils;

/**
 * @program: frame-parent
 * @description: 消息推送帮助类
 * @author: huangligui
 * @create: 2019-01-29 10:52
 **/
public class RtpHelper {




    /**
     * 给某个话题推送消息
     * topicId 需要为完整的 @see TopicBuild.generateTopicId
     *
     * @param topicId 话题ID
     * @param routeKey 某个服务
     * @param msgType 消息类型
     * @param context 消息类容
     */
    public static void send(String topicId,String routeKey,String msgType,Object context)
    {
        Message msg = RtpHelper.MessageBuild.create(msgType,context);
        send(topicId,routeKey,msg);
    }

    public static void send(String topicId,String msgType,Object context)
    {
        send(topicId,AppContextHelper.getAppCode(),msgType,context);
    }

    /**
     *  发送消息，给当前服务发送
     *
     * @param topicId
     * @param msg
     */
    public static void send(String topicId,Message msg)
    {
       send(topicId,AppContextHelper.getAppCode(),msg);
    }

    public static void sendToUser(String topicId,Message msg)
    {
        send(TopicBuild.generateTopicId(topicId,Topic.TYPE_USER,null),msg);
    }

    public static void send(String topicId,String routeKey,Message msg)
    {
        SendMessageEvent event = new SendMessageEvent();
        event.setTopicId( TopicBuild.topicIdConvert( topicId ));
        event.setRouteKey(routeKey);
        event.setMessage(msg);
        EventPublisherHelper.publish(event);

    }

    /**
     * 自定topic
     *
     * @param topic
     */
    public static void createTopic(ITopic topic)
    {
        TopicCreateEvent event = new TopicCreateEvent();

        event.setTopic(topic);

        EventPublisherHelper.publish(event);
    }

    /**
     * 创建话题，appCode为空代表是一个公共的话题
     * @param id
     * @param appCode
     */
    public static void createTopic(String id,String appCode)
    {
        ITopic topic = TopicBuild.createTopic(id,Topic.TYPE_CUS,appCode);
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
        TopicDelEvent event = new TopicDelEvent();
        event.setTopicId( TopicBuild.topicIdConvert(id));
        EventPublisherHelper.publish(event);
    }

    /**
     *  话题之间绑定
     *  注意 该topicId 属于完整的全Id
     *  可以认为是 加入房
     *
     *  把 topicId 转发给 destId
     *
     * @param topicId
     * @param destId
     * @param routeKey
     */
    public static void bind(String topicId, String destId, String routeKey)
    {
        BindEvent event = new BindEvent();
        event.setRouteKey(routeKey);
        event.setDestId(TopicBuild.topicIdConvert(destId));
        event.setTopicId(TopicBuild.topicIdConvert(topicId));
        EventPublisherHelper.publish(event);
    }

    public static void unBind(String topicId, String destId,String routeKey)
    {
        UnBindEvent event = new UnBindEvent();
        event.setTopicId(TopicBuild.topicIdConvert(topicId));
        event.setDestId(TopicBuild.topicIdConvert(destId));
        event.setRouteKey(routeKey);
        EventPublisherHelper.publish(event);
    }


    public static class MessageBuild{

        public static Message create(String msgType, Object context)
        {
            Message msg = new Message();
            msg.setContext(context);
            msg.setType(msgType);
            msg.setOrigin(AppContextHelper.getAppCode());
            msg.setTimestamp(System.currentTimeMillis());
            msg.setSendUserId(FworkHelper.getUid());
            msg.setSendUserName(FworkHelper.geUserName());
            return msg;
        }
    }

    public static class TopicBuild {

        public static ITopic createTopic(String id,String type,String appCode)
        {
           return createTopic(id,type,appCode,true);
        }

        public static ITopic createTopic(String id,String type,String appCode,Boolean autoDelete)
        {
            Topic topic = new Topic();
            topic.setAppCode(appCode);

            topic.setType(type);
            String tid = generateTopicId(id,type,appCode);
            topic.setId(tid);
            topic.setName(tid);
            topic.setAutoDelete(autoDelete);
            return topic;
        }

        public static String topicIdConvert(String id)
        {
            String tid = id;
           if( tid.indexOf(ITopic.TOPIC_SPLIT) != -1 )
           {
                return tid;
           }
           tid = generateTopicId(id,Topic.TYPE_CUS,AppContextHelper.getAppCode());
           return tid;
        }
        public static String generateTopicId(String id,String type,String appCode)
        {
            StringBuffer bf = new StringBuffer();

            if( StringUtils.isNotEmpty(type) )
            {
                bf.append(type).append(ITopic.TOPIC_SPLIT);
                if( StringUtils.isNotEmpty(id) ) {
                    id = id.replace("^" + type  + "\\" +  ITopic.TOPIC_SPLIT, "");
                }
            }
            if( StringUtils.isNotEmpty(appCode) )
            {
                bf.append(appCode).append(ITopic.TOPIC_SPLIT);
                if( StringUtils.isNotEmpty(id) && !"*".equals(appCode) ) {
                    id = id.replaceAll(appCode + ITopic.TOPIC_SPLIT, "");
                }
            }
            if( StringUtils.isNotEmpty(id) )
            {
                bf.append(id);
            }

            return bf.toString();
        }
        public static String generateUserTopicId(String id)
        {
            return generateTopicId(id,ITopic.TYPE_USER,null);
        }
        public static String generateTopicId(String id)
        {
            return generateTopicId(id,ITopic.TYPE_CUS,AppContextHelper.getAppCode());
        }

        public static ITopic createTopic(String id)
        {
            return createTopic(id,Topic.TYPE_CUS,AppContextHelper.getAppCode());
        }

        public static ITopic createTopic(String id,Boolean autoDelete)
        {
            return createTopic(id,Topic.TYPE_CUS,AppContextHelper.getAppCode(),autoDelete);
        }
        public static String getCurrUserTopicId()
        {
            return RtpHelper.TopicBuild.generateTopicId( FworkHelper.getUser().getAccount(),ITopic.TYPE_USER,null );
        }

    }
}
