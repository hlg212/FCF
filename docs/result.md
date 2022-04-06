# 返回结果
对返回结果进行统一封装,对前端更友好

减少对业务代码的入侵，不用手动构建返回的数据

`public Integer count(@RequestParamOrBody Q queryProperty);`
实际数据在【data】 字段中

```json
{
"success": true,
"code": "200",
"msg": "",
"data": 0
}
```
## 功能介绍

分页数据
```json
{
  "success": true,
  "code": "200",
  "msg": "SUCCESS",
  "data": {
    "pageNum": 1,
    "pageSize": 2,
    "total": 8,
    "pages": 4,
    "size": 2,
    "list": [
      {
        "id": "1511234502794956802",
        "code": "123",
        "createTime": "2022-04-05 00:00:00",
        "updateTime": "2022-04-05 00:00:00"
      },
      {
        "id": "1511234984317833218",
        "code": "123",
        "createTime": "2022-04-05 00:00:00",
        "updateTime": "2022-04-05 00:00:00"
      }
    ]
  }
}
```
单条数据
```json
{
  "success": true,
  "code": "200",
  "msg": "SUCCESS",
  "data": {
    "id": "1511234502794956802",
    "code": "123",
    "createTime": "2022-04-05 00:00:00",
    "updateTime": "2022-04-05 00:00:00"
  }
}
```

业务异常
```
{
"success": false,
"code": "500",
"msg": "名称不能为空!",
"data": 0
}
```
服务异常`data`包含异常日志信息,后面可以对异常进行检索
```json
{
  "success": false,
  "code": "500",
  "msg": "org.springframework.transaction.UnexpectedRollbackException:Transaction rolled back because it has been marked as rollback-only",
  "data": "basic#2022-04-06#20220458bc7c0fed3f41ba8800e7a192cfd281",
}
```
## 例子
