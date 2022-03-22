package  io.github.hlg212.fcf.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁工具
 *
 * @author huangligui
 * @date 2020年5月30日
 */
@Slf4j
public class LockHelper {
	
	private static Map<String,ReentrantLock> lockMap = new ConcurrentHashMap();
	
	private static Object M_LOCK = "$$__LOCKHELPER_M_LOCK";
	
	public static void lock(String key){
		ReentrantLock lock = getLock(key);
		lock.lock();
	}

	private static ReentrantLock getLock(String key)
	{
		ReentrantLock lock = lockMap.get(key);

		if( lock == null )
		{
			synchronized ( M_LOCK)
			{
				lock = lockMap.get(key);
				if( lock == null )
				{
					lock = new ReentrantLock();
					lockMap.put(key, lock);
				}
			}
		}
		return lock;
	}
	
	public static void unlock(String key)
	{
		ReentrantLock lock = lockMap.get(key);
		lock.unlock();
		if( lock.getQueueLength() == 0 )
		{
			clear(key);
		}
	}

	public static boolean tryLock(String key,long milliseconds)
	{
		ReentrantLock lock = getLock(key);
		Boolean isLock = false;
		try {
			isLock =  lock.tryLock(milliseconds,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			log.warn(e.getMessage(),e);
		}
		return isLock;
	}

	public static boolean tryLock(String key)
	{
		ReentrantLock lock = getLock(key);
		return lock.tryLock();
	}
	
	
	public static void clear(String key)
	{
		lockMap.remove(key);
	}
}
