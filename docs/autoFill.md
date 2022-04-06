# 自动填充
增强mybatis-plus的自动填充，提供了常用的填充字段;  
可以在配置中进行填充字段配置  
一般情况下自动填充 创建人，创建时间，更新人，更新时间等  
## 功能介绍
一般统一在common-db.yml中配置  
fcf.dao.fill: insert,update 代表新增,修改时哪些字段需要填充  
框架内置了 createTime,createUser,createUserId 等自动填充的接口  
通过 field.updateTime 可以指定填充字段内容  
如：  
数据插入时需要填充testid字段,testid填充的值由框架内置的填充器处理了，直接填充当前用户ID(createUserId)  
```yaml
fcf:
  dao:
    fill:
      field:
        createUserId:
          - testid

      insert:
        - testid
```

## 例子
