# 查询条件对象
FCF 将数据库的查询条件与前段的参数结合起来，命名QCO

为了简化SQL编写与前段参数的转换，按照约定的方式进行命名，框架自动根据命名转换成SQL

## 功能介绍
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
```

## 例子
