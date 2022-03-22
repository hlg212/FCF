package  io.github.hlg212.fcf.conf;

import  io.github.hlg212.fcf.annotation.EsbConditional;
import  io.github.hlg212.fcf.cache.CacheHandler;
import  io.github.hlg212.fcf.cache.CacheHandlerReg;
import  io.github.hlg212.fcf.cache.CacheListenerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * @program: frame-parent
 * @description:  缓存监听相关
 * 依赖 esb 事件总线
 * @author  huangligui
 * @create: 2019-03-01 10:32
 **/
@Configuration
public class CacheListenerConfig {

    private Map<String,List<CacheHandler>> cacheHandlers = new HashMap();

    @Autowired(required = false)
    public void setCacheHandlers(List<CacheHandlerReg> res)
    {
        Iterator<CacheHandlerReg> iter =  res.iterator();
        while( iter.hasNext() )
        {
            addCacheHandlerReg( iter.next() );
        }
    }

    private void addCacheHandlerReg(CacheHandlerReg reg)
    {
        List list = cacheHandlers.get(reg.getCacheName());
        if( list == null )
        {
            list = new ArrayList();
            cacheHandlers.put(reg.getCacheName(),list);
        }
        list.add(reg.getCacheHandler());
    }

    @Bean
    public CacheListenerImpl cacheListener()
    {
        return new CacheListenerImpl(new CacheHandlerExt());
    }

    private class CacheHandlerExt implements CacheHandler
    {

        @Override
        public void onPut(String cacheName, String key, String value) {
            List<CacheHandler>  list= cacheHandlers.get(cacheName);
            if( list != null ) {
                Iterator<CacheHandler> iter = list.iterator();
                while( iter.hasNext() )
                {
                    iter.next().onPut(cacheName,key,value);
                }
            }

        }

        @Override
        public void onReload(String cacheName) {
            List<CacheHandler>  list= cacheHandlers.get(cacheName);
            if( list != null ) {
                Iterator<CacheHandler> iter = list.iterator();
                while (iter.hasNext()) {
                    iter.next().onReload(cacheName);
                }
            }
        }

        @Override
        public void onRefresh(String cacheName, String key) {
            List<CacheHandler>  list= cacheHandlers.get(cacheName);
            if( list != null ) {
                Iterator<CacheHandler> iter = list.iterator();
                while (iter.hasNext()) {
                    iter.next().onRefresh(cacheName, key);
                }
            }
        }

        @Override
        public void onRemove(String cacheName, String key) {
            List<CacheHandler>  list= cacheHandlers.get(cacheName);
            if( list != null ) {
                Iterator<CacheHandler> iter = list.iterator();
                while( iter.hasNext() )
                {
                    iter.next().onRemove(cacheName,key);
                }
            }
        }

        @Override
        public void onDel(String cacheName) {
            List<CacheHandler>  list= cacheHandlers.get(cacheName);
            if( list != null ) {
                Iterator<CacheHandler> iter = list.iterator();
                while (iter.hasNext()) {
                    iter.next().onDel(cacheName);
                }
            }
        }
    }

}
