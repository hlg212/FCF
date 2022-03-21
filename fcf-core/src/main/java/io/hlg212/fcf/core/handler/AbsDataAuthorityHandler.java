package io.hlg212.fcf.core.handler;

import io.hlg212.fcf.api.AuthApi;
import io.hlg212.fcf.core.properties.DataAuthorityProperties;
import io.hlg212.fcf.core.util.TableHelper;
import io.hlg212.fcf.dao.BaseDao;
import io.hlg212.fcf.model.Qco;
import io.hlg212.fcf.model.QcoSuffix;
import io.hlg212.fcf.model.QueryCondition;
import io.hlg212.fcf.model.QueryParam;
import io.hlg212.fcf.model.dam.IDataAuthorityConfigSet;
import io.hlg212.fcf.model.dam.IDataAuthorityPropertyCondition;
import io.hlg212.fcf.model.dam.IDataAuthorityPropertyConditionValue;
import io.hlg212.fcf.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 *
 * 数据权限处理接口
 *
 *  add 是在新增之后
 *  update 是在更新之前
 *  delete 是在删除之前，条数需要能对应上
 *  query 是在查询中添加额外条件
 *
 * @author huangligui
 * @date 2021年1月11日
 */
@Slf4j
public abstract class AbsDataAuthorityHandler implements DataAuthorityHandler {

    @Autowired
    private AuthApi authApi;

    private Map<BaseDao, IDataAuthorityConfigSet> dataAuthorityConfigSets;

    @Autowired
    protected DataAuthorityProperties dataAuthorityProperties;

    private Map<String, Map<BaseDao, List<IDataAuthorityPropertyCondition>>> dataAuthorityConditions = new HashMap<>();
    ;
    private Map<BaseDao, List<IDataAuthorityPropertyCondition>> addDataAuthorityConditions = new HashMap<>();
    private Map<BaseDao, List<IDataAuthorityPropertyCondition>> updateDataAuthorityConditions = new HashMap<>();
    private Map<BaseDao, List<IDataAuthorityPropertyCondition>> deleteDataAuthorityConditions = new HashMap<>();
    private Map<BaseDao, List<IDataAuthorityPropertyCondition>> queryDataAuthorityConditions = new HashMap<>();


    private static final String LOCK_KEY = "DAMDATAAUTHORITYHANDLER_LOAD_KEY";

    private static final String AUTHORIITY_ADD = "ADD";
    private static final String AUTHORIITY_UPDATE = "UPDATE";
    private static final String AUTHORIITY_DELETE = "DELETE";
    private static final String AUTHORIITY_QUERY = "QUERY";

    @Override
    public void onAdd(Object o, BaseDao dao) {
        boolean flag = tryCheck(o, dao, AUTHORIITY_ADD);
        if (!flag) {
            ExceptionHelper.throwBusinessException("新增受数据权限影响，新增失败! ");
        }
    }

    @Override
    public void onUpdate(Object o, BaseDao dao) {
        boolean flag = tryCheck(o, dao, AUTHORIITY_UPDATE);
        if (!flag) {
            ExceptionHelper.throwBusinessException("更新受数据权限影响，修改失败! ");
        }
    }

    @Override
    public void onDelete(Object ids, BaseDao dao) {
        boolean flag = tryCheck(ids, dao, AUTHORIITY_DELETE);
        if (!flag) {
            ExceptionHelper.throwBusinessException("删除受数据权限影响，删除失败! ");
        }

    }

    private boolean tryCheck(Object o, BaseDao dao, String type) {
        boolean flag = true;
        try {
            if (checkConfigSet(dao, type)) {
                flag = isOwner(type, dao, o);
            }
        } catch (Exception e) {
            if (dataAuthorityProperties.isSkipException()) {
                log.warn("尝试添加数据权限出现异常,根据配置跳过!", e);
            } else {
                ExceptionHelper.throwServerException(e);
            }
        }
        return flag;
    }

    @Override
    public void onQuery(QueryParam queryParam, BaseDao dao) {
        if (checkConfigSet(dao, AUTHORIITY_QUERY)) {
            try {
                IDataAuthorityConfigSet configSet = dataAuthorityConfigSets.get(dao);
                List<QueryCondition> conditions = getDataAuthorityPropertyConditions(AUTHORIITY_QUERY, dao);
                log.info("dao[{}]，注入[{}]条权限控制代码!", configSet.getCode(), conditions.size());
                queryParam.getConditions().addAll(conditions);
            } catch (Exception e) {
                if (dataAuthorityProperties.isSkipException()) {
                    log.warn("尝试添加数据权限出现异常,根据配置跳过!", e);
                } else {
                    ExceptionHelper.throwServerException(e);
                }
            }
        }
    }


    private boolean checkAuthoriity(String daoName, String authority) {
        return false;
    }

    private boolean isOwner(String type, BaseDao dao, Object val) {
        IDataAuthorityConfigSet configSet = dataAuthorityConfigSets.get(dao);
        List<QueryCondition> conditions = getDataAuthorityPropertyConditions(type, dao);
        if( conditions.isEmpty() ) {
            return true;
        }
        log.info("dao[{}]，注入[{}]条权限控制代码!", configSet.getCode(), conditions.size());
        if (AUTHORIITY_DELETE.equals(type)) {
            conditions.add(createConditionById(null, val, dao));
        } else {
            conditions.add(createCondition(val, dao));
        }
        Qco qco = new Qco();
        qco.setConditions(conditions);
        int count = dao.count(qco);
        if (AUTHORIITY_DELETE.equals(type)) {
            if (count != ((Object[]) val).length) {
                return false;
            }
        } else {
            if (count <= 0) {
                return false;
            }
        }
        return true;
    }

    private QueryCondition createCondition(Object o, BaseDao dao) {
        String keyPro = TableHelper.getKeyProperty(dao.getModelClass());
        Object value = null;
        try {
            value = PropertyUtils.getProperty(o, keyPro);
        } catch (Exception e) {
            ExceptionHelper.throwServerException(e);
        }
        return createConditionById(keyPro, value, dao);
    }

    private QueryCondition createConditionById(String keyPro, Object ids, BaseDao dao) {
        if (StringUtils.isEmpty(keyPro)) {
            keyPro = TableHelper.getKeyProperty(dao.getModelClass());
        }
        if( ids instanceof  Collection) {
            return new QueryCondition(keyPro, QueryParam.IN, ids);
        }
        return new QueryCondition(keyPro, QueryParam.EQUALS, ids);
    }


    private List<QueryCondition> getDataAuthorityPropertyConditions(String type, BaseDao dao) {
        List<IDataAuthorityPropertyCondition> list = dataAuthorityConditions.get(type).get(dao);
        List<QueryCondition> conditions = convert(list, dao);
        return conditions;
    }

    private boolean checkConfigSet(BaseDao dao, String type) {
        Boolean flag = false;
        IDataAuthorityConfigSet configSet = null;
        try {
            init();
            configSet = dataAuthorityConfigSets.get(dao);
        } catch (Exception e) {
            if (dataAuthorityProperties.isSkipException()) {
                log.warn("尝试添加数据权限出现异常,根据配置跳过!", e);
                return flag;
            } else {
                ExceptionHelper.throwServerException(e);
            }
        }

        if (configSet != null) {

            if (AUTHORIITY_QUERY.equals(type)) {
                flag = configSet.getIsQuery() ;
            } else if (AUTHORIITY_ADD.equals(type)) {
                flag = configSet.getIsAdd();
            } else if (AUTHORIITY_UPDATE.equals(type)) {
                flag = configSet.getIsUpdate();
            } else if (AUTHORIITY_DELETE.equals(type)) {
                flag = configSet.getIsDelete();
            }
            log.info("dao[{}]开启了[{}]数据权限控制,将增加权限代码控制!", configSet.getCode(), type);
            if (checkAuthoriity(configSet.getCode(), type)) {
                log.info("用户拥有[{}]的数据[{}]权限,跳过数据权限相关控制!", configSet.getCode(), type);
            }

        }
        return flag == null ? false : flag;
    }


    abstract protected Object getDynamicValue(IDataAuthorityPropertyCondition propertyCondition);

    private List<QueryCondition> convert(List<IDataAuthorityPropertyCondition> list, BaseDao dao) {
        List<QueryCondition> conditions = new ArrayList<>();
        if (list != null) {
            for (IDataAuthorityPropertyCondition propertyCondition : list) {
                IDataAuthorityPropertyConditionValue conditionValue = propertyCondition.getValue();
                Object value = null;
                if (IDataAuthorityPropertyCondition.TYPE_DYNAMIC.equals(propertyCondition.getType())) {
                    value = getDynamicValue(propertyCondition);
                } else {
                    Class proType = FieldHelper.getFieldType(dao.getModelClass(), propertyCondition.getPropertyName());
                    value = toValue(conditionValue.getValue().toString(), proType);
                }
                if( value != null ) {
                    QueryCondition condition = createQueryCondition(propertyCondition, value);
                    if (condition != null) {
                        conditions.add(condition);
                    }
                }
            }
        }
        return conditions;
    }


    private Object toValue(String str, Class proType) {
        Object v = null;
        if (str.startsWith("[")) {
            v = JsonHelper.parseObject(str);
        } else {
            if (Date.class.isAssignableFrom(proType)) {
                v = DateHelper.parseDate(str);
            } else if (Integer.class.isAssignableFrom(proType)) {
                v = Integer.parseInt(str);
            }else if (Long.class.isAssignableFrom(proType)) {
                v = Long.parseLong(str);
            }
            else
            {
                v = str;
            }
        }
        if (v instanceof Collection) {
            Collection listv = (Collection) v;
            List result = new ArrayList();
            for (Object strv : listv) {
                result.add(toValue(strv.toString(), proType));
            }
            v = result;
        }

        return v;
    }


    private QueryCondition createQueryCondition(IDataAuthorityPropertyCondition propertyCondition, Object value) {
        QueryCondition condition = new QueryCondition();
        String propertyName = propertyCondition.getPropertyName();
        String names[] = propertyName.split(".");
        if (names.length > 1) {
            condition.setProperty(names[1]);
            condition.setPrefix(names[0]);
        } else {
            condition.setProperty(propertyName);
        }
        String proOperation = "xx" + propertyCondition.getOperation();
        condition.setOperation(QcoSuffix.get(proOperation).getOperation());

        condition.setValue(value);
        return condition;
    }

    abstract protected <S extends IDataAuthorityConfigSet> List<S> getDataAuthorityConfigSet();

    abstract protected <C extends IDataAuthorityPropertyCondition> List<C> getDataAuthorityPropertyConditions(IDataAuthorityConfigSet set);

    protected void refresh() {
        dataAuthorityConfigSets = null;
        init();
    }

    protected void init() {
        if (dataAuthorityConfigSets != null) return;

        LockHelper.lock(LOCK_KEY);
        try {
            if (dataAuthorityConfigSets != null) return;
            List<IDataAuthorityConfigSet> dataAuthorityConfigSets = getDataAuthorityConfigSet();
            Map<BaseDao, IDataAuthorityConfigSet> map = new HashMap();
            for (IDataAuthorityConfigSet set : dataAuthorityConfigSets) {
                BaseDao dao = (BaseDao) SpringHelper.getBean(set.getCode());
                if (dao != null) {
                    List<IDataAuthorityPropertyCondition> conditions = getDataAuthorityPropertyConditions(set);

                    List<IDataAuthorityPropertyCondition> addList = new ArrayList<>();
                    List<IDataAuthorityPropertyCondition> updateList = new ArrayList<>();
                    List<IDataAuthorityPropertyCondition> deleteList = new ArrayList<>();
                    List<IDataAuthorityPropertyCondition> queryList = new ArrayList<>();
                    for (IDataAuthorityPropertyCondition condition : conditions) {
                        if (condition.getIsAdd()) {
                            addList.add(condition);
                        }
                        if (condition.getIsUpdate()) {
                            updateList.add(condition);
                        }
                        if (condition.getIsDelete()) {
                            deleteList.add(condition);
                        }
                        if (condition.getIsQuery()) {
                            queryList.add(condition);
                        }
                    }

                    addDataAuthorityConditions.put(dao, addList);
                    updateDataAuthorityConditions.put(dao, updateList);
                    deleteDataAuthorityConditions.put(dao, deleteList);
                    queryDataAuthorityConditions.put(dao, queryList);

                    map.put(dao, set);
                }

            }
            dataAuthorityConditions.put(AUTHORIITY_ADD, addDataAuthorityConditions);
            dataAuthorityConditions.put(AUTHORIITY_UPDATE, updateDataAuthorityConditions);
            dataAuthorityConditions.put(AUTHORIITY_DELETE, deleteDataAuthorityConditions);
            dataAuthorityConditions.put(AUTHORIITY_QUERY, queryDataAuthorityConditions);
            this.dataAuthorityConfigSets = map;
        } finally {
            LockHelper.unlock(LOCK_KEY);
        }


    }

}
