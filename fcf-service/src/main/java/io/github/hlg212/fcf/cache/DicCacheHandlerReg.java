package  io.github.hlg212.fcf.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: frame-parent
 * @description: 缓存处理注册
 * 一个缓存可以有多个处理
 * @author  huangligui
 * @create: 2019-03-01 10:15
 **/
@Slf4j
@Component
public class DicCacheHandlerReg implements CacheHandlerReg   {

    @Autowired
    private DicCacheHandler dicCacheHandler;

    @Override
    public String getCacheName() {
        return Constants.Dic;
    }

    @Override
    public CacheHandler getCacheHandler() {
        return dicCacheHandler;
    }
}
