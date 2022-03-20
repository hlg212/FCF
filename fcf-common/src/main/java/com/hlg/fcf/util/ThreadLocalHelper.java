package com.hlg.fcf.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装线程数据.
 * <p>
 * 如果在自定义的线程中调用了该方法,注意执行清理.
 *
 * @author huangligui
 * @date 2016-8-23 上午10:36:40
 */
public class ThreadLocalHelper {

    private static class Holder {
        private Map data = new HashMap();
    }

    private static ThreadLocal _tl = new ThreadLocal();

    private static Holder getHolder() {
        Holder h = (Holder) _tl.get();
        if (h == null) {
            h = new Holder();
            _tl.set(h);
        }
        return h;
    }

   /* public static Object get(Object k) {
        return getHolder().data.get(k);
    }*/

    public static <T> T get(Object k) {
        return (T) getHolder().data.get(k);
    }

    
    public static void set(Object k, Object v) {
        getHolder().data.put(k, v);
    }

    public static void del(Object k) {
        getHolder().data.remove(k);
    }
    
    public static <T> T take(Object k) {
    	T t = get(k);
    	del(k);
    	return t;
    }

    /**
     *  清理线程数据，一般写在线程退出时
     *  注意： 除非线程由你自己控制，否则不要随便的调用该方法
     */
    public static void clear() {
        getHolder().data.clear();
        _tl.remove();
    }

}
