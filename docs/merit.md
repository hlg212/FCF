# 优点特性

## 统一结果与异常
返回结果[统一封装](result.md)，不对代码产生入侵；   
`public Integer count(@RequestParamOrBody Q queryProperty);`  
实际数据在【data】 字段中   
```
{
  "success": true,
  "code": "200",
  "msg": "",
  "data": 0
}
```

## 统一接口
框架顶层设计了 Query 、Curd 接口  
将 Dao、Service、Controller、Api 等不同层的操作保持了一致  
采用了泛型的方式，使每一层面向的数据结构不同，但接口却又能保持一致，编写起来更友好简单  

## 接口入侵小
采用interface default 的方式提供接口，不产生入侵  

## 代码减少
零代码实现增删改查导入导出  
基于领域三层模型提供了常用的业务框架接口  
DAO 、Service 、Controller 都有统一的 增删改查接口  
采用默认方法的方式提供接口，不会对业务代码产生入侵  

## 隐式注入与转换
DAO 自动注入到 Service, Service 自动注入到 Controller  
按照分层模型，每层操作自己层的模型数据  
DAO 操作 PO  
Service 操作 BO  

## 认证透传
前端认证等用户环境信息透传  

## 事件通知模型
考虑了微服务中事件隔离与事件订阅  
spring cloud bus 实现的事件模型  
为每个业务级别隔离创建消息通道  
定义了粒度更小模型支持  

## 缓存,刷新模型
考虑了微服务中缓存隔离与共享  
将业务操作与缓存刷新操作进行分离  

## 日志与链路
自动收集日志  
集成skwalking日志收集，将调用链与日志进行结合

## 文档
基于swagger，自动将字段进行映射，提供swagger认证  
GET采用from展开参数列表，方便开发调试  
POST采用body json方式，方便开发  

## 集成
基于spring autoConfiguration规范提供自动装配  
可以将多个服务打包到一起，作为一个服务运行，不影响源码开发模式  
