package io.hlg212.fcf.service.impl;

import io.hlg212.fcf.Curd;
import io.hlg212.fcf.annotation.CacheRemove;
import io.hlg212.fcf.constants.FrameCommonConstants;
import io.hlg212.fcf.model.Model;
import io.hlg212.fcf.model.Qco;
import org.springframework.cache.annotation.CachePut;
import org.springframework.transaction.annotation.Transactional;

/**
 * 增删改查 整合适配器接口
 * 如果只需要查询接口，请使用 QueryService
 *
 * @author huangligui
 * @date 2020年8月18日
 */
public interface CurdServiceImpl<T extends Model> extends io.hlg212.fcf.service.CurdService<T>,QueryServiceImpl<T>  {

    @Override
    @Transactional
    @CachePut(keyGenerator=FrameCommonConstants.PK_ID_GENERATOR)
    default public T save(T t) {
        return getDao().save(t);
    }

    @Override
    @Transactional
    @CacheRemove(key="#p0")
   default public void deleteById(Object... id) {
        getDao().deleteById(id);
    }

    @Override
    @Transactional
    @CachePut(keyGenerator=FrameCommonConstants.PK_ID_GENERATOR)
    default public T update(T t) {
        return getDao().update(t);
    }

}
