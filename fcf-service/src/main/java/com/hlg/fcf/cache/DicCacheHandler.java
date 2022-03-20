package com.hlg.fcf.cache;

import com.hlg.fcf.service.DicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: frame-parent
 * @description:  字典缓存处理
 * 当字典被修改时，会发送 事件 ，然后转到该处理器
 * 因为字典使用了本地缓存，所以需要手工处理
 * @author: huangligui
 * @create: 2019-03-01 10:15
 **/
@Slf4j
@Component
public class DicCacheHandler extends CacheHandlerAdapter   {

    @Autowired
    private DicService dicService;

    @Override
    public void onPut(String cacheName, String key, String value) {
        log.debug("put cache:{} , key:{} ,",cacheName,key);

/*        if( check(key) ) {
            key = key.substring(Constants.DicKey.getAllDics_prefix.length());
            dicService.refresh(key);
        }*/
    }

    @Override
    public void onRefresh(String cacheName, String key) {
        log.debug("refresh cache:{} , key:{} ,",cacheName,key);
        if( check(key) ) {
            key = key.substring(Constants.DicKey.getAllDics_prefix.length());
            dicService.refresh(  key );
        }

    }

    @Override
    public void onRemove(String cacheName, String key) {
        log.debug("remote cache:{} , key:{} ,",cacheName,key);
        if( check(key) ) {
            key = key.substring(Constants.DicKey.getAllDics_prefix.length());
            dicService.refresh(key);
        }
    }

    private boolean check(String key)
    {
        if( !key.startsWith(Constants.DicKey.getAllDics_prefix) )
        {
            return false;
        }
        return true;
    }

}
