# 配置分级
默认开启配置中心  
配置进行分级：公共配置，服务配置  
本地服务(.yml)除了spring.application.name以外不保存任何配置  
服务配置优先公共配置生效  
## 功能介绍
配置中心中约定了common配置  
`
- common
- common-rc
- common-application
- common-db
- common-mq
- common-redis
- common-actuator
- common-oauth
- common-esb
- common-log
`

配置分类存储，分类清晰

通过公共配置可以对服务进行统一管理，如：指定 服务端口 等

```yaml
fcf:
  application:
    config:
      port: 8181
server:
  #端口
  port: ${fcf.application.${spring.application.name}.port:8080}
```
## 例子
