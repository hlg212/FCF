package io.hlg212.fcf.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2019-03-01 10:15
 **/
@Slf4j
@Component
public class LongRunningResCacheHandlerReg implements CacheHandlerReg {

    @Autowired
    private LongRunningResCacheHandler handler;

    @Override
    public String getCacheName() {
        return Constants.LongRunningRes;
    }

    @Override
    public CacheHandler getCacheHandler() {
        return handler;
    }
}
