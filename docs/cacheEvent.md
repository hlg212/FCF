# 缓存事件
缓存操作(新增，修改，删除)都会产生事件  
基于[事件总线]()的方式发布事件、订阅  
只订阅本服务使用到的缓存  
## 功能介绍
缓存的操作会触发 CachePutEvent、CacheRemoveEvent 等事件  
这些事件会传递到订阅了该缓存的其它服务  
框架自动订阅了服务中使用的缓存事件,如果特别关心只需要实现接口处理即可  
```java
public class XxCacheHandler extends CacheHandlerAdapter

public class XxCacheHandlerReg implements CacheHandlerReg
```


## 例子
