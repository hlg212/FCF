package  io.github.hlg212.fcf.cache;

/**
 * @program: frame-parent
 * @description: 缓存处理接口定义
 * @author  huangligui
 * @create: 2019-03-01 09:49
 **/
public interface CacheHandler {

    public void onPut(String cacheName,String key,String value);

    public void onReload(String cacheName);

    public void onRefresh(String cacheName,String key);

    public void onDel(String cacheName);

    public void onRemove(String cacheName,String key);
}
