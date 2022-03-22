package  io.github.hlg212.fcf.service.impl;

import  io.github.hlg212.fcf.ISerializable;
import  io.github.hlg212.fcf.model.ImpExpModel;
import  io.github.hlg212.fcf.service.CurdService;
import  io.github.hlg212.fcf.util.DbHelper;
import  io.github.hlg212.fcf.util.ExceptionHelper;
import  io.github.hlg212.fcf.util.SpringHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.ResolvableType;

import java.util.Collection;

/**
 * 导入数据保存方式
 *
 * @author huangligui
 * @date 2020年8月18日
 */
@Slf4j
class ImportSave {

    static public void importSave(ImpExpModel impExpModel) {
        importSave(impExpModel.getDatas(),impExpModel.getEntityClass());
    }
    static public void importSave( Collection datas,Class entityClass) {
        log.warn("目前使用的是默认导入保存方法，请尽快根据业务重写该实现!");
        String idName = DbHelper.getModelPkId(entityClass);
        if (StringUtils.isEmpty(idName)) {
            ExceptionHelper.throwBusinessException("找不到ID属性，无法执行默认逻辑!");
        }
        try {
            CurdService curdService = SpringHelper.getBeanByResolvableType(ResolvableType.forClassWithGenerics(CurdService.class,entityClass));
            if( curdService == null )
            {
                ExceptionHelper.throwBusinessException("{}未实现save相关接口!");
            }
            for (Object o : datas) {
                Object id = PropertyUtils.getProperty(o, idName);
                importSave(o, id,curdService);
            }
        } catch (Exception e) {
            ExceptionHelper.throwServerException(e);
        }

    }

   static private void importSave(Object data, Object id,CurdService curdService) {
        Object old = null;
        if (id != null && StringUtils.isNotEmpty(id.toString())) {
            old = curdService.getById(id);
        }
        if (old == null) {
            log.info("数据{}不存在,执行新增", id);
            curdService.save((ISerializable) data);
        } else {
            log.info("数据{}已存在,执行更新!", id);
            curdService.update((ISerializable)data);
        }
    }
}
