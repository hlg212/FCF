# CURDIE
基于[FCF](https://github.com/hlg212/FCF) 实现的增、删、改、查、导入、导出例子  
零代码实现一个基础的功能模块  
[工程启动与下载](https://github.com/hlg212/fcf-examples)  
## 服务如何启动
首先启动[配置中心](https://github.com/hlg212/FCP/tree/master/config) ,在bootstrap.yml中增加如下配置(可以采用host 方式指定)：  
```yaml
fcf:
  config:
    uri: http://localhost:8181/config/repo
```
如果没有部署配置中心,请采用[本地配置](https://github.com/hlg212/fcf-examples/local) 的方式启动。  


## 效果展示

![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/curdie_swagger.jpg)

![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/curdie_facade.jpg)

![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/curdie_controller.jpg)

![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/curdie_service.jpg)

![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/curdie_serviceImpl.jpg)

![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/curdie_dao.jpg)

![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/curdie_mapper.jpg)

![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/curdie_export.jpg)


## License

See the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) file for details.