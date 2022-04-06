# FCF 是什么
fast cloud frame(FCF) 是一个基于spring cloud 的快速开发框架  
框架对于cloud开发中的一些比较繁琐与容易产生歧义的功能与设计进行了整合与约束   
FCF 提供了集成度比较高开发基础功能与接口  
FCF 针对微服务中的痛点与难点进行了优化与增强，将复杂的微服务架构简单化，使开发人员的学习成本降低  
FCF 提供了一套可行的约定与规范，基于规范不仅使开发的代码质量更高，而且开发的代码量也更少  
遵循FCF开发规范与约定也可将微服务转为传统单服务  

## 如何使用
[更多例子](examples.md)

pom引用依赖：  

```xml
<dependency>
    <groupId>io.github.hlg212.fcf</groupId>
    <artifactId>fcf-starter</artifactId>
    <version>${fcf.version}</version>
</dependency>
```

## 搭配FCP
[FCP](https://github.com/hlg212/FCP) 是一个基于FCF框架的微服务平台  
提供了业务开发中需要的基础功能，如: 用户、机构、权限 等  
提供微服务开发的基础组件，如： 网关、配置中心、注册中心 等  
提供中间件服务相关的一些功能实现，如： 任务调度、网关路由、文件、推送 等  
