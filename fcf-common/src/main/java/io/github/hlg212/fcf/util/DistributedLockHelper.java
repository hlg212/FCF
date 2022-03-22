package  io.github.hlg212.fcf.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 分布式锁帮助类
 * 采用redis实现的分布式锁，默认key只跟服务相关
 * 如果想使用公共锁，请调用 commonKeyConvert 生成
 * 请注意，使用锁一定要释放
 *
 * @author  huangligui
 * @create: 2019-02-25 16:01
 **/
@Slf4j
public class DistributedLockHelper {

    private static RedisLockRegistry redisLockRegistry;

    private static final String NAMESPACE = "htcf-lock";

    private static final int DEF_MAX_SECONDS = 60000;

    private static final String COMMON_KEY_PREFIX = "COMMON2020228_";

    private static RedisLockRegistry getRedisLockRegistry()
    {
        if( redisLockRegistry == null )
        {
            redisLockRegistry = new RedisLockRegistry(SpringHelper.getBean(RedisConnectionFactory.class), NAMESPACE,DEF_MAX_SECONDS);
        }
        return redisLockRegistry;
    }

    public static void lock(String key)
    {
        Lock lock = getRedisLockRegistry().obtain(keyConvert(key));
        lock.lock();
    }

    public static void unlock(String key)
    {
        Lock lock = getRedisLockRegistry().obtain(keyConvert(key));
        lock.unlock();
    }

    public static boolean tryLock(String key,long milliseconds)
    {
        Lock lock = getRedisLockRegistry().obtain(keyConvert(key));
        Boolean isLock = false;
        try {
            isLock =  lock.tryLock(milliseconds,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
        }
        return isLock;
    }

    public static String commonKeyConvert(String key)
    {
        if( isCommonKey(key) )
        {
            return key;
        }
        return COMMON_KEY_PREFIX + key;
    }

    private static String keyConvert(String key)
    {
        if( isCommonKey(key) )
        {
            return key;
        }
        return AppContextHelper.getAppCode() + "_" + key;
    }

    private static boolean isCommonKey(String key)
    {
        if( key.startsWith(COMMON_KEY_PREFIX) )
        {
            return true;
        }
        return false;
    }

}
