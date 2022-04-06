# 定时任务
定义了定时任务接口，业务只需要使用，入侵小  
可以通过手工的方式触发调度  
与[FCP](https://github.com/hlg212/FCP) 结合可以自动注册任务  
## 功能介绍
编写一个定时任务简单,只需要实现 Task接口即可  
通过注解`@TaskRegister`还可以自动(也可手动)进行调度注册([FCP-定时任务]())  
任务日志与任务异常都会自动收集  
```java
@TaskRegister(cron = "0/3 * * * * ? ",name = "打印Hello的任务")
public class HelloTask implements Task {

    @Override
    public String executeTask(String param) {
        System.out.println("每三秒打印一次Hello");
        return null;

    }
}
```

## 例子
