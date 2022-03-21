# FCF
 fast cloud frame(FCF) 是一个基于spring cloud 的快速开发框架。
 
 框架对于cloud开发中的一些比较繁琐与容易产生歧义的功能与设计进行了整合与约束。
 
 FCF 提供了集成度比较高开发基础功能与接口。

 使用FCF可以快速的开发微服务。
 
 遵循FCF开发规范与约定也可将微服务转为传统单服务。

 ## Links

- [Documentation]
- [Code Generator]
- [Samples]
- [FCP 基础开发平台]

## 功能介绍
-  数据统一与异常
-  接口API框架定义
	- 提供了统一的接口定义与实现，各层面对的接口基本一致
-  增强了feign接口
	- 增加了多重继承的实现
	- 增强了泛型的识别
    - 自动拆包
-  分层领域模型自动注入
    - 不同的层接口操作不同的数据模型
	- DAO 接口操作为 PO 类型
	- Service 接口操作为 BO 类型数据
-  查询对象，将字段映射SQL,降低业务SQL开发量。前端也更易理解
    - userNameLike:xx   ->    user_name like '%xx%';  
	- cjsjOrder:asc ->  cjsj order by "asc"	
-  提供了定时任务的定义支持,隐藏了实现依赖
    - FCP 提供定时任务实现,基于 xxl-job  
-  事件通知模型、消息队列
    - 基于 spring cloud bus 实现的事件模型  
      为每个业务级别隔离创建消息通道  
      定义了粒度更小模型支持
-  集成skwalking日志收集，将调用链与日志进行结合
-  API文档基于swagger，自动将字段进行映射
    - 提供swagger认证
-  基于spring autoConfiguration规范提供自动装配
    - 微服务开发模式，单服务运行
	- 无需调整代码
-  采用默认方法的方式提供接口，不会对业务代码产生入侵
	- 例如：
	常规： public class DemoController extends CurdieController<DemoBo, DemoQco>
	
	FCF：  public class DemoController implements CurdieController<DemoBo, DemoQco>

## 功能演示

## 问题反馈


## License

See the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) file for details.