# 条件映射
与 QCO 进行搭配使用   
QCO属性命名中带有条件，框架会自动进行转换

## 功能介绍

|  条件   | 说明  |  属性名  |  数据类型  | sql效果  |
|  ----  | ----  | ----  | ----  |----  |
| = | 等于 | name |任意,非集合  | name = #name |
| !=  | 不等于 | nameNotEq | 任意,非集合 | name != #name |
| is  | 判空 | nameIs | 字符="null",其它任意not | name is null;name is not null |
| like  | 模糊 | nameLike | 字符 | name like '%#name%'  |
| LtLike  | 左模糊 |nameLtLike | 字符 | name like '%#name' |
| RtLike  | 右模糊 |nameRtLike | 字符 | name like '#name%' |
| in  | 集合 | nameIn | 集合类型,List,或者数组[]  | name in (#namep[0],#name[n])  |
| not in  | - |nameNotIn | 集合类型List,或者数组[] | name not in (#namep[0],#name[n])  |
| `<` | 小于 |nameLt | 数字或时间 | name < #name |
| `<=`  | 小于等于 |nameLtEq | 数字或时间 | name <= #name  |
| `>`  | 大于 |nameGt | 数字或时间 | name > #name  |
| `>=`  | 大于等于 |nameGtEq | 数字或时间 | name >= #name |
| between  | 范围 |nameBetween | 数字或时间的数组或集合,但只取2位 | name between (#namep[0],#name[1]) |
| order  | 排序 |nameOrder | 字符="DESC",其它升序 |  order by name desc; order by name asc;  |

## 例子
