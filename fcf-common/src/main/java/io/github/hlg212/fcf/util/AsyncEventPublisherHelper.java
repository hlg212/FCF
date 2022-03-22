package  io.github.hlg212.fcf.util;

import  io.github.hlg212.fcf.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 异步实现发布帮助类
 *
 * @author huangligui
 * @date  2022年3月21日 下午2:41:09
 */
@Component
@Slf4j
public class AsyncEventPublisherHelper {

    private static AsyncEventPublisherHelper _instance;

    private EventPublishThread eventPublishThread;

    private BlockingQueue<BaseEvent> queue;

    private static AsyncEventPublisherHelper getInstance()
    {
        if( _instance == null )
        {
            _instance = SpringHelper.getBean(AsyncEventPublisherHelper.class);
        }
        return _instance;
    }

    public  AsyncEventPublisherHelper()
    {
        eventPublishThread = new EventPublishThread();
        queue  = new LinkedBlockingQueue(10000);
        eventPublishThread.start();
    }

    public static void publish(BaseEvent event)
    {
        getInstance().queue.add(event);
    }

    private class EventPublishThread extends Thread{
        @Override
        public void run() {
            BaseEvent event = null;
            while( true )
            {
                try {
                    if( event == null ) {
                        event = queue.take();
                    }
                    EventPublisherHelper.publish(event);
                    event = null;
                } catch (Exception e) {
                    log.warn("发布失败，1000ms之后尝试再进行!", e);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                }

            }
        }
    }



}
