package  io.github.hlg212.fcf.service.impl;

import  io.github.hlg212.fcf.dao.BaseDao;
import  io.github.hlg212.fcf.model.Model;
import  io.github.hlg212.fcf.model.PageInfo;
import io.github.hlg212.fcf.model.PageQuery;
import  io.github.hlg212.fcf.model.Qco;
import  io.github.hlg212.fcf.util.ServiceDaoHelper;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


/**
 * 查询接口
 * 常规模块开发，请使用 CurdService、CurdieService
 *
 * @see CurdieServiceImpl
 * @see CurdServiceImpl
 * @author huangligui
 * @date 2020年8月18日
 */
public interface QueryServiceImpl<T extends Model>extends  io.github.hlg212.fcf.service.QueryService<T> {

    default  <S extends BaseDao<T>>S  getDao()
    {
        return (S)ServiceDaoHelper.getDao(getClass(),BaseDao.class);
    }

    @Override
    @Cacheable(key="#p0")
    default public <E extends T> E getById(Object id) {
        return getDao().getById(id);
    }

    @Override
    default public <E extends T> E get(Qco queryProperty) {
        return getDao().get(queryProperty);
    }

    @Override
    default public Integer count(Qco queryProperty) {
        return getDao().count(queryProperty);
    }

    @Override
    default public <E extends T> List<E> find(Qco queryProperty) {
        return getDao().find(queryProperty);
    }

    @Override
    default public  <E extends T> PageInfo<E> findPage(PageQuery<Qco> pageQuery)
    {
        return getDao().findPage(pageQuery);
    }
}
