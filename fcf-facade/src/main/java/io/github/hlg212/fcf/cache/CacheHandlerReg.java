package  io.github.hlg212.fcf.cache;

/**
 * 缓存处理注册器定义
 *
 * @author  huangligui
 * @create: 2019-03-01 09:49
 **/
public interface CacheHandlerReg {
    public String getCacheName();

    public CacheHandler getCacheHandler();
}
