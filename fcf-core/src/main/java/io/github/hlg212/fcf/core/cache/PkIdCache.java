package  io.github.hlg212.fcf.core.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  实体主键Id 缓存类 
 *
 * @author  huangligui
 * @create: 2020-03-30 16:01
 */
public class PkIdCache {

	private static PkIdCache pkIdCache;
	
	private final Map<String, String> idMap = new ConcurrentHashMap<>();
	
	private PkIdCache() {}
	
	public static PkIdCache getInstance() {
		if(pkIdCache != null) {
			return pkIdCache;
		}
		synchronized (PkIdCache.class) {
			if(pkIdCache == null) {
				pkIdCache = new PkIdCache();
			}
		}
		return pkIdCache;
	}
	
	
	public void put(String className, String pkId) {
		idMap.put(className, pkId);
	}
	
	public String get(String className) {
		return idMap.get(className);
	}
}
