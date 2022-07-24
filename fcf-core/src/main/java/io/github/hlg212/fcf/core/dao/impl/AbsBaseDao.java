package  io.github.hlg212.fcf.core.dao.impl;

import io.github.hlg212.fcf.core.handler.DataAuthorityHandler;
import  io.github.hlg212.fcf.core.util.QueryPropertyParseUtils;
import  io.github.hlg212.fcf.core.util.TableHelper;
import  io.github.hlg212.fcf.dao.BaseDao;
import  io.github.hlg212.fcf.model.*;
import  io.github.hlg212.fcf.util.DbHelper;
import  io.github.hlg212.fcf.util.ExceptionHelper;
import  io.github.hlg212.fcf.util.FieldHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  框架DAO的接口默认实现
 *
 * ClassName: AbsBaseDao
 * date: 2019年7月10日 上午8:50:16
 *
 * @author huangligui
 */
@Slf4j
public abstract class AbsBaseDao<T extends Model> implements BaseDao<T> {

    public final static int MAX_PAGE_SIZE_DEFAULT = 10000;

    private Class modelClass;
    private Map<String, String> asMap;

    @Autowired(required = false)
    protected DataAuthorityHandler dataAuthorityHandler;


    @Override
    public T save(T t) {
        int i = 0;
        try {
            i = insert(t);
        } catch (Exception e) {
            ExceptionHelper.throwServerException("数据库新增记录异常", e);
        }
        if (i == 0) {
            ExceptionHelper.throwServerException("数据库操作,insert返回0");
        }
        if( dataAuthorityHandler != null )
        {
            dataAuthorityHandler.onAdd(t,this);
        }
        return t;
    }

    @Override
    public T update(T t) {
        if( dataAuthorityHandler != null )
        {
            dataAuthorityHandler.onUpdate(t,this);
        }
        String fieldName = this.getPrimeryKeyField();
        updateById(t);
        Object id = null;
        try {
            id =  PropertyUtils.getProperty(t,fieldName);
        } catch (Exception e) {
            log.error("找不到id:{}",id);
        }
        if( id == null )
        {
            return t;
        }
        return this.getById(id);
    }

    @Override
    public void deleteById(Object... id) {
        if( dataAuthorityHandler != null )
        {
            dataAuthorityHandler.onDelete(id,this);
        }
        //事先获取缓存实体对应主键属性，以免第一次执行delete时出错
        if (id.length > 1) {
            deleteBatchIds(Arrays.asList(id));
        } else {
            deleteById(id[0]);
        }

    }

    abstract protected int deleteById(Object id);

    abstract protected int deleteBatchIds(Collection ids);

    abstract protected int insert(T t);

    abstract protected int updateById(T t);

    abstract protected <E extends T> E selectById(Object id);

    @Override
    public <E extends T> E getById(Object id) {
        return selectById(convertIdType(id));
    }

    @Override
    public <E extends T> E get(Qco qco) {
        PageQuery<Qco> pageQuery = new PageQuery<>();
        pageQuery.setQco(qco);
        pageQuery.setPageNum(0);
        pageQuery.setPageSize(1);
        QueryParam queryParam = convertQueryParam(pageQuery);
        // get 不走权限过滤
        PageInfo<E> page = this.findPage(queryParam);
        List<E> list = page.getList();
        if (list == null || list.size() == 0) {
            //List返回类型默认为com.github.pagehelper.Page 若查询结构集为空时,强制返回null对象
            return null;
        }
        return list.get(0);
    }

    private void checkQueryCondition(QueryParam queryParam) {
        List<QueryCondition> list = queryParam.getConditions();
        if (list == null || list.isEmpty()) {
            return;
        }
        Class<T> clazz = getModelClass();
        List<QueryCondition> resultList = new ArrayList<>(list.size());
        Map<String, String> asMap = getFieldAsMapping();
        for (QueryCondition condition : list) {
            if (StringUtils.isBlank(condition.getProperty())) {
                continue;
            }
            String fieldName = this.getFieldName(condition.getProperty());
            if (asMap.containsKey(fieldName)) {
                String asFieldName = asMap.get(fieldName);
                condition.setProperty(condition.getProperty().replace(fieldName, asFieldName));
                fieldName = asFieldName;
            }
            Field classField = FieldUtils.getField(clazz, fieldName, true);
            if (classField == null) {
                log.warn("{}中无属性{}, 忽略其属性查询条件的设置值{}", clazz, fieldName, condition.getValue());
                continue;
            }
//            String columnName = TableHelper.getColumn(clazz, fieldName);
//            if (fieldName.length() != columnName.length()) {
//                condition.setProperty(condition.getProperty().replace(fieldName, columnName));
//            }
            if(  condition.getValue() != null ) {
                if (Date.class.isAssignableFrom(classField.getType())) {
                    if (condition.getValue() instanceof String[]) {
                        String[] values = (String[])condition.getValue();
                        Date[] dates = new Date[2];

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dates[0] = formatter.parse(values[0], new ParsePosition(0));
                        dates[1] = formatter.parse(values[1], new ParsePosition(0));
                        condition.setValue(dates);
                    }
                }
            }

            //增加查询别名t,跟动态生成sql保持一致
//			if(!condition.getProperty().startsWith("t.")) {
//				condition.setProperty("t."+condition.getProperty());
//			}
            resultList.add(condition);
        }
        queryParam.setConditions(resultList);
    }

    private Map<String, String> getFieldAsMapping() {
        if (asMap == null) {
            Class<T> clazz = getModelClass();
            asMap = DbHelper.getFieldAsMapping(clazz);
        }
        return asMap;
    }

    private String getFieldName(String property) {
        if (property.contains(".")) {
            return property.substring(property.indexOf(".") + 1);
        }
        return property;
    }


    @Override
    public <E extends T> List<E> find(Qco qco) {
        PageQuery<Qco> pageQuery = new PageQuery<>();
        pageQuery.setQco(qco);
        pageQuery.setPageNum(0);
        pageQuery.setPageSize(MAX_PAGE_SIZE_DEFAULT);
        PageInfo<E> page = this.findPage(pageQuery);
        List<E> list = page.getList();
        return list;
    }

    @Override
    public <E extends T> PageInfo<E> findPage(PageQuery<Qco> pageQuery) {
        QueryParam param = convertQueryParam(pageQuery);
        if( dataAuthorityHandler != null )
        {
            dataAuthorityHandler.onQuery(param,this);
        }
        return this.findPage(param);
    }

    private QueryParam convertQueryParam(PageQuery<Qco> pageQuery)
    {
        QueryParam param = QueryPropertyParseUtils.convertForQueryParam(pageQuery.getQco());
        param.setPageNum(pageQuery.getPageNum());
        param.setPageSize(pageQuery.getPageSize());
        this.checkQueryCondition(param);
        return param;
    }

    abstract protected <E extends T> PageInfo<E> findPage(QueryParam queryParam);

    @Override
    public Integer count(Qco queryProperty) {
        QueryParam queryParam = QueryPropertyParseUtils.convertForQueryParam(queryProperty);
        this.checkQueryCondition(queryParam);
        return this.count(queryParam);
    }

    abstract protected Integer count(QueryParam queryParam);

    @Override
    public Class<T> getModelClass() {
        if (modelClass != null) {
            return modelClass;
        }
        Class ifs[] = this.getClass().getInterfaces();
        for (int i = 0; i < ifs.length; i++) {
            Class intfs = ifs[i];
            Type type[] = intfs.getGenericInterfaces();
            modelClass = getModelClass(type);
            if (modelClass != null) {
                return modelClass;
            }
            Type selfType[] = intfs.getTypeParameters();
            modelClass = getModelClass(selfType);
            if (modelClass != null) {
                return modelClass;
            }
        }
        ExceptionHelper.throwServerException("请在当前类或基类上增加PO泛型类型");
        return null;
    }

    private Class getModelClass(Type type[]) {
        if (type != null && type.length > 0) {
            if (type[0] instanceof ParameterizedType) {
                ParameterizedType p = (ParameterizedType) type[0];
                return (Class<T>) p.getActualTypeArguments()[0];
            }
            if (type[0] instanceof TypeVariable) {
                TypeVariable v = (TypeVariable) type[0];
                if (v.getBounds() != null && v.getBounds().length > 0) {
                    try {
                        return Class.forName(v.getBounds()[0].getTypeName());
                    } catch (ClassNotFoundException e) {

                    }
                }
            }
        }

        return null;
    }

    abstract protected String getPrimeryKeyField();



    /**
     * 将传入的主键值，转换为实际对应的类型
     * @param id
     *  
     */
    private Object convertIdType(Object id) {
        if (id == null) {
            ExceptionHelper.throwBusinessException("传入主键值不能为空");
        }
        try {
            Class<?> clazz = this.getModelClass();

            String fieldName = TableHelper.getKeyProperty(clazz);
            Class<?> paramClass = FieldHelper.getFieldType(clazz, fieldName);
            if (Integer.class.isAssignableFrom(paramClass)) {
                return Integer.parseInt(id.toString());
            } else if (String.class.isAssignableFrom(paramClass)) {
                return String.valueOf(id);
            } else if (Long.class.isAssignableFrom(paramClass)) {
                return Long.parseLong(id.toString());
            }
        } catch (Exception e) {
            ExceptionHelper.throwServerException("请检查主键值:" + id + "的传入类型", e);
        }
        return id;
    }

}
