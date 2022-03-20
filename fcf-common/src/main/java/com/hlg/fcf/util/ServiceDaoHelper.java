package com.hlg.fcf.util;

import com.hlg.fcf.dao.BaseDao;
import com.hlg.fcf.service.FrameService;
import org.springframework.core.ResolvableType;
/**
 * 通过泛型的方式，动态获取service,dao
 *
 *
 * @author huangligui
 * @date 2020年8月18日
 */
public class ServiceDaoHelper {
    private static  final String DAO_SUFFIX = "Dao";

    public static  <S extends FrameService>S  getService(Class srcClass, Class serviceClass)
    {
        return SpringUtils.getBeanByResolvableType(srcClass,serviceClass);
    }

    public static  <S extends BaseDao>S  getDao(Class srcClass, Class daoClass) {
        BaseDao dao = SpringUtils.getBeanByResolvableType(srcClass, daoClass);
        if (dao == null) {
            String cacheId = generateCacheId(srcClass,daoClass);

            if( daoClass.getTypeParameters().length == 0 ) {
                dao = (BaseDao)SpringUtils.getBean(daoClass);
            }
            else {
                Class bc = ParameterizedTypeHelper.getParameterizedTypeIndex0(srcClass);
                Class pc = ModelHelper.findModel(bc, ModelHelper.ModelType.PO);
                ResolvableType type = ResolvableType.forClassWithGenerics(daoClass, pc);
                dao = getDaoByResolvableType(type);
            }
            CacheDataHelper.put(cacheId,dao);
        }
        return (S)dao;
    }

    private static String generateCacheId(Class srcClass,Class destClass)
    {
        String cacheId = srcClass.getName() + ":" + destClass.getName();
        return cacheId;
    }


    private static <S extends Object> S getDaoByResolvableType(ResolvableType type)
    {
        String beans[] = SpringUtils.getApplicationContext().getBeanNamesForType(type);

        if(beans.length == 0)
        {
            return null;
        }
        if( beans.length > 1 )
        {
            String daoName = type.getGeneric(0).resolve().getSimpleName() + DAO_SUFFIX;
            for( String bname : beans )
            {
                if( daoName.equalsIgnoreCase(bname) )
                {
                    return (S) SpringUtils.getBean(bname);
                }
            }
            return (S) SpringUtils.getBean(beans[beans.length - 1]);
        }
        return (S) SpringUtils.getBean(beans[0]);
    }

}
