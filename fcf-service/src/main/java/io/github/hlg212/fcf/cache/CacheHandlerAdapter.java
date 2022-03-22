package  io.github.hlg212.fcf.cache;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: frame-parent
 * @description: 缓存处理适配器
 * @author  huangligui
 * @create: 2019-03-01 10:15
 **/
@Slf4j
public class CacheHandlerAdapter implements CacheHandler   {
    @Override
    public void onPut(String cacheName, String key, String value) {
        log.debug("put cache:{} , key:{} ,",cacheName,key);

    }

    @Override
    public void onReload(String cacheName) {

        log.debug("reload cache:{} ",cacheName);
    }

    @Override
    public void onRefresh(String cacheName, String key) {

        log.debug("refresh cache:{} , key:{} ,",cacheName,key);
    }

    @Override
    public void onDel(String cacheName) {
        log.debug("del cache:{} ",cacheName);
    }

    @Override
    public void onRemove(String cacheName, String key) {
        log.debug("remove cache:{} , key:{} ,",cacheName,key);
    }

}
