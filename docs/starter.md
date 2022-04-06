# starter
fcf会扫描约定的目录,无需开发再手动开启注解配置，如 mybatis目录

按照约定的包结构，多个不同的服务都可以进行集成，无需进行修改
`com.hlg.demo` `com.abc.efg.demo`
├─Application.java # 一般启动类放最外层目录
├─mode
│  ├─bo
│  ├─po
├─dao  # 自动扫描该层
├─event # 自动扫描
├─service # 自动扫描代理

## 功能介绍
通过以下代码与就能将服务制作成支持starter模式

不用为不同目录结构的服务重新设置扫描等
```java
@CloudApplicationScan
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {Application.class}))
public class AppAutoConfiguration 

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.htcf.curdie.AppAutoConfiguration
```

## 例子
