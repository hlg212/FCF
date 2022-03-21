package io.hlg212.fcf.util;

import io.hlg212.fcf.event.RemoteEvent;
import io.hlg212.fcf.event.RemoteEventTopicHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.*;

/**
 * 事件topic的帮助类
 * 通过 @RemoteEventTopicHandler 接口；
 * 只要有一个接口返回了就不会在调用后续的
 *
 * @author: huangligui
 * @create: 2019-02-25 16:01
 **/
public class EventTopicHandleHelper {

    private static List handlers = null;

    private static List getHandlers()
    {
        if( handlers == null )
        {
            synchronized (EventTopicHandleHelper.class) {
                Collection<RemoteEventTopicHandler> coll = SpringHelper.getApplicationContext().getBeansOfType(RemoteEventTopicHandler.class).values();
                handlers = new ArrayList();
                handlers.addAll(coll);
                AnnotationAwareOrderComparator.sort(handlers);
            }
        }
        return handlers;
    }

    public static String getTopicKey(RemoteEvent event)
    {
        Iterator iter =  getHandlers().iterator();
        String topicKey = null;
        while(  iter.hasNext() )
        {
            RemoteEventTopicHandler h =(RemoteEventTopicHandler)iter.next();
            topicKey = h.eventHandle(event);
            if(StringUtils.isNotEmpty(topicKey)){
                break;
            }
        }
        return topicKey;
    }
}
