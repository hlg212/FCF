# 查询条件对象
FCF 将数据库的查询条件与前段的参数结合起来，命名QCO

为了简化SQL编写与前段参数的转换，按照约定的方式进行命名，框架自动根据命名转换成SQL
Qco是框架专门为了简化开发人员编写SQL而设计的一个对象模型，它与框架接口进行适配

[更多条件映射](qco_condtion.md)   

## 功能介绍
1. 在很多框架中，采用DTO接受前端传递过来的参数，后端再根据参数拼接SQL，前端无需了解SQL
通过在代码(或者mapper脚本)中进行参数判断，然后决定采用什么条件；
```
{id:"123",name:"n123"}

-- sql 
where id = "123" and name like "%n123%"
或
where id = "123" and name = "n123"
```
2. 还有一种设计是将所有的条件开放给前端，前端可以随意组装查询条件
这种虽然灵活性很高(后端不需要编写SQL代码)，但降低了服务的安全控制，而且需要前端对SQL有一些了解
而且对于复杂sql无能为力(比较难)
```
[{name:"id",condition:"=",value:"123"},
{name:"name",condition:"like",value:"n123"}]
--sql
where id = "123" and name like "%n123%"
```

两种方式各有优劣：
第一种：不需要前端了解SQL，逻辑全由后端控制。
    但到了后期，查看该接口需要结合代码才能知道真正参数的条件

第二种：需要前端了解SQL知识，加大了前端学习成本，前端权限太大
    到了后期，想测试某个业务接口，编写条件的难度也比较大（接口文档无法直观的显示条件参数）；


本框架考虑结合两种的方式，既不需要后端拼接SQL代码，也不需要前端去了解SQL，同时到能通过接口文档直观的了解到参数条件
实用性，安全性都得到了兼顾，文档也很直观

## 代码介绍
代码
```java
@Data
public class UserQco extends Qco {
	
	private static final long serialVersionUID = 1L;
	@Field(description = "账号等于")
	private String account;

	@Field(description = "账号Like")
	private String accountLike;

	@Field(description = "创建时间排序")
	private String createTimeOrder="desc";
}
```
swagger展示
![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/swagger_qco.jpg)

sql代码
```sql
sql execution Time:0==>SELECT  *  FROM T_USER           ORDER BY create_time DESC limit 10
sql execution Time:0==>SELECT  *  FROM T_USER WHERE accountLike like '%123%'          ORDER BY create_time DESC limit 10
```

## 例子
