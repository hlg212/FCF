
package  io.github.hlg212.fcf.core.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import  io.github.hlg212.fcf.core.util.TableHelper;
import  io.github.hlg212.fcf.model.Model;
import  io.github.hlg212.fcf.model.OrderCondition;
import  io.github.hlg212.fcf.model.PageInfo;
import  io.github.hlg212.fcf.model.QueryParam;
import  io.github.hlg212.fcf.util.ExceptionHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;


/**
 *  框架DAO的封装实现
 *
 * ClassName: BaseDaoImpl
 * date: 2019年7月10日 上午8:50:16
 *
 * @author huangligui
 */
@Slf4j
public class BaseDaoImpl<T extends Model> extends AbsBaseDao<T> {

    public BaseDaoImpl() {

    }


    @Override
    protected int updateById(T t) {
        BaseMapper mapper = getMapper();
        return mapper.updateById(t);
    }

    @Override
    protected int insert(T t) {
        BaseMapper mapper = getMapper();
        return mapper.insert(t);
    }

    @Override
    protected int deleteById(Object id) {
        BaseMapper mapper = getMapper();
        return mapper.deleteById((Serializable) id);
    }

    @Override
    protected int deleteBatchIds(Collection ids) {
        BaseMapper mapper = getMapper();
        return mapper.deleteBatchIds(ids);
    }

    @Override
    protected <E extends T> E selectById(Object id) {
        BaseMapper mapper = getMapper();
        return (E) mapper.selectById((Serializable) id);
    }

    @Override
    protected Integer count(QueryParam queryParam) {
        BaseMapper mapper = getMapper();
        return mapper.selectCount(queryParamToWrapper(queryParam));
    }

    private QueryWrapper queryParamToWrapper(QueryParam queryParam) {
        CustomParamQueryWrapper queryWrapper = new CustomParamQueryWrapper();
        queryWrapper.setCustomParam(queryParam.getCustomParam());

        queryParam.getConditions().forEach(queryCondition -> {

            String column = getColumn( queryCondition.getProperty() );
            if( StringUtils.isEmpty(column) ){
                ExceptionHelper.throwServerException(String.format("属性[%s]列不存在",queryCondition.getProperty()));
            }
            if( StringUtils.isNotEmpty(queryCondition.getPrefix()) )
            {
                column = queryCondition.getPrefix() + "." + column;
            }

            switch (queryCondition.getOperation()) {
                case QueryParam.EQUALS:
                    queryWrapper.eq(column, queryCondition.getValue());
                    break;
                case QueryParam.NOT_EQUALS:
                    queryWrapper.ne(column, queryCondition.getValue());
                    break;
                case QueryParam.IS:
                    if (QueryParam.NULL.equals(queryCondition.getValue())) {
                        queryWrapper.isNull(column);
                    } else {
                        queryWrapper.isNotNull(column);
                    }
                    break;

                case QueryParam.GREATER_THAN:
                    queryWrapper.gt(column, queryCondition.getValue());
                    break;
                case QueryParam.GREATER_THAN_OR_EQUAL:
                    queryWrapper.ge(column, queryCondition.getValue());
                    break;
                case QueryParam.IN:
//                    final int size;
////
////                    if( queryCondition.getValue() instanceof  Object[])
////                    {
////                        size = ((Object[]) queryCondition.getValue()).length;
////                        queryWrapper.in(column, (Object[])queryCondition.getValue());
////                    }
////                    else
////                    {
////                        size = ((Collection) queryCondition.getValue()).size();
////                        queryWrapper.in(column, (Collection) queryCondition.getValue());
////                    }
                    applyBigIn(queryWrapper,queryCondition.getValue(),column,QueryParam.IN);
                    break;
                case QueryParam.NOT_IN:
//                    if( queryCondition.getValue() instanceof  Object[])
//                    {
//                        queryWrapper.notIn(column, (Object[])queryCondition.getValue());
//                    }
//                    else
//                    {
//                        queryWrapper.notIn(column, (Collection) queryCondition.getValue());
//                    }
//                    break;

                    applyBigIn(queryWrapper,queryCondition.getValue(),column,QueryParam.NOT_IN);
                    break;
                case QueryParam.LESS_THAN:
                    queryWrapper.lt(column, queryCondition.getValue());
                    break;
                case QueryParam.LESS_THAN_OR_EQUAL:
                    queryWrapper.le(column, queryCondition.getValue());
                    break;
                case QueryParam.LIKE:
                    queryWrapper.like(column, queryCondition.getValue());
                    break;
                case QueryParam.RT_LIKE:
                    queryWrapper.likeRight(column, queryCondition.getValue());
                    break;
                case QueryParam.LT_LIKE:
                    queryWrapper.likeLeft(column, queryCondition.getValue());
                    break;

                case QueryParam.BETWEEN:
                    Object value = queryCondition.getValue();

                    if ((value instanceof Collection<?>)) {
                        Object[] arr = ((Collection) value).toArray();

                        queryWrapper.between(column, arr[0], arr[1]);
                    } else if (value.getClass().isArray()) {
                        Object[] arr = (Object[]) value;
                        queryWrapper.between(column, arr[0], arr[1]);
                    }

                    break;
                default:
                    break;
            }
        });
        applySql(queryWrapper,queryParam);
        setOrder(queryWrapper, queryParam);
        return queryWrapper;
    }


    private void applyBigIn(QueryWrapper<T> queryWrapper,Object inValues,String column,String op)
    {
        final int size;
        if( inValues instanceof  Object[])
        {
            size = ((Object[]) inValues).length;
        }
        else
        {
            size = ((Collection) inValues).size();
        }
        queryWrapper.and(wrapper -> {
            int begin = 0;
            int end = 0;
            int pageSize = 500;
            Object[] values = null;;
            if( inValues instanceof  Object[])
            {
                values = (Object[]) inValues;
            }
            else
            {
                values = ((Collection)inValues).toArray();
            }
            for( int i=0;end < size;i++)
            {
                begin = i * pageSize;
                end = (i + 1) * pageSize;
                if( end > size )
                {
                    end = size;
                }
                if( QueryParam.IN.equals( op) )
                {
                    wrapper.or().in(column, Arrays.copyOfRange(values,begin,end));
                }
                else
                {
                    wrapper.or().notIn(column, Arrays.copyOfRange(values,begin,end));
                }

            }
            return wrapper;

        });
    }


    private void applySql(QueryWrapper wrapper, QueryParam queryParam) {
        if( queryParam.getSqlConditions() != null && !queryParam.getSqlConditions().isEmpty()) {
            queryParam.getSqlConditions().stream().forEach(sql -> {
                wrapper.apply(sql);
            });
        }
    }

    private void setOrder(QueryWrapper wrapper, QueryParam queryParam) {
        queryParam.getOrderConditions();
        for(OrderCondition orderCondition : queryParam.getOrderConditions() )
        {
            String column =  getColumn(orderCondition.getName());
            if( StringUtils.isNotEmpty(orderCondition.getPrefix()) )
            {
                column = orderCondition.getPrefix() + "." + column;
            }
            if (QueryParam.DESC.equalsIgnoreCase(orderCondition.getValue())) {
                wrapper.orderByDesc(column);
            }
            else {
                wrapper.orderByAsc(column);
            }
        }
    }

    @Override
    protected <E extends T> PageInfo<E> findPage(QueryParam queryParam) {
        IPage page = new Page(queryParam.getPageNum(), queryParam.getPageSize());
        BaseMapper mapper = getMapper();
        page = mapper.selectPage(page, queryParamToWrapper(queryParam));

        // 用PageInfo对结果进行包装
        return new PageInfo<E>(queryParam.getPageNum(), queryParam.getPageSize(), page.getTotal(), page.getRecords());
    }

    @Override
    protected String getPrimeryKeyField() {

        Class<?> clazz = this.getModelClass();

        String fieldName = TableInfoHelper.getTableInfo(clazz).getKeyProperty();
        return fieldName;

    }
    private String getColumn(String pro)
    {
        Class<?> clazz = this.getModelClass();
        return TableHelper.getColumn(clazz,pro);
    }

    @Override
    public <M> M getMapper() {
        return null;
    }


    @Data
    class CustomParamQueryWrapper extends QueryWrapper<T> {
        private Object customParam;
    }
}
