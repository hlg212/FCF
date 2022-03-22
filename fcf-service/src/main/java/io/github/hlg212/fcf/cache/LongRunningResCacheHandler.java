package  io.github.hlg212.fcf.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author  huangligui
 * @create: 2019-03-01 10:15
 **/
@Slf4j
@Component
public class LongRunningResCacheHandler extends CacheHandlerAdapter {

    @Autowired
    private LongRunningResCacheHandler self;


    @Override
    public void onRemove(String cacheName, String key) {
        if( check(key) )
        {
            self.clearSimple();
        }
    }

    @Override
    public void onRefresh(String cacheName, String key) {
        log.debug("remote cache:{} , key:{} ,",cacheName,key);
        if( check(key) )
        {
            self.clearSimple();
        }
    }

    @Override
    public void onDel(String cacheName) {
        self.clearSimple();
    }

    private boolean check(String str)
    {
        if( str.startsWith(Constants.LongRunningResKey.getAllLongRunningRes_prefix) )
        {
            return true;
        }
        return false;
    }

    @CacheEvict(value =Constants.LongRunningRes,allEntries = true,cacheManager = Constants.CacheManager.SimpleCacheManager)
    public void clearSimple()
    {
        log.debug("clear clearSimple:{} ,",Constants.LongRunningRes);
    }

}
