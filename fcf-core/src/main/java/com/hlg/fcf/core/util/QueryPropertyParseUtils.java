/**
 *
 */
package com.hlg.fcf.core.util;

import com.hlg.fcf.model.*;
import com.hlg.fcf.util.BeanHelper;
import com.hlg.fcf.util.FieldHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * QueryProperty解析器工具类
 *
 * @author changwei
 * @date 2018年9月7日
 */
@Slf4j
public class QueryPropertyParseUtils {

    private static QueryCondition convertForQueryCondition(String propertyName, Object value) {
        if (propertyName == null || value == null) {
            return null;
        }
        QcoSuffix queryPropertyName = QcoSuffix.get(propertyName);
        if (queryPropertyName == null) {
            return new QueryCondition(propertyName, QueryParam.EQUALS, value);
        } else if (StringUtils.isNotBlank(queryPropertyName.getOperation())
                && propertyName.length() > queryPropertyName.getOperation().length()) {
            String suffix = queryPropertyName.getSuffix();
            propertyName = propertyName.substring(0, propertyName.length() - suffix.length());
            String valueStr = value.toString();
            if (QcoSuffix.BETWEEN.getSuffix().equals(suffix)) {
                if (value instanceof String) {
                    String[] valueArr = valueStr.split(" - ");
                    value = valueArr;
                }
                if (!checkBetweenValue(value)) {
                    return null;
                }
            } else if (QcoSuffix.IN.getSuffix().equals(suffix)) {
                if (value instanceof String[]) {
                    Object[] valueArr = (Object[]) value;
                    if (valueArr.length < 1) {
                        return null;
                    }
                }
            }
            return new QueryCondition(propertyName, queryPropertyName.getOperation(), value);
        }
        return null;
    }

    private static boolean checkBetweenValue(Object value) {
        if (value == null) {
            log.warn("between 条件值解析不和格式忽略其条件值设定. value:{}", value);
            return false;
        }
        if ((value instanceof Collection<?>) && ((Collection<?>) value).size() >= 2
                || (value.getClass().isArray() && ((Object[]) value).length >= 2)) {
            return true;
        }
        log.warn("between 条件值解析不和格式忽略其条件值设定. value:{}", value);
        return false;
    }


    public static QueryParam convertForQueryParam(Qco queryProperty) {
        QueryParam queryParam = new QueryParam();
        if (queryProperty == null) {
            return queryParam;
        }

        List<QueryCondition> conditions =  new ArrayList<>(queryProperty.getConditions());
        queryParam.setConditions(conditions);
        conditions.addAll( getQueryConditions(queryProperty) );

        queryParam.setOrderConditions(queryProperty.getOrders());
        queryParam.setCustomParam(queryProperty);
        return queryParam;
    }

    private static List<QueryCondition> getQueryConditions(Qco queryProperty)
    {
        Class qcoClass = queryProperty.getClass();
        List<QueryCondition> conditions = new ArrayList<>();
        try {
            Introspector.getBeanInfo(queryProperty.getClass());
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        Collection<String> coll = BeanHelper.getFieldNames(queryProperty.getClass());
        //PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(queryProperty.getClass());
        for (String  propertyName : coll) {
            if ("class".equalsIgnoreCase(propertyName) ||
                    "conditions".equalsIgnoreCase(propertyName) ||
                    "orders".equalsIgnoreCase(propertyName) ||
                    "propertyMap".equalsIgnoreCase(propertyName)
                    ) {
                continue;
            }
            Object value = null;
            try {
                value = PropertyUtils.getProperty(queryProperty, propertyName);
            } catch (Exception e) {

            }
            if (value == null || (value instanceof String && "".equals(value))) {
                continue;
            }
            if (propertyName.endsWith(QcoSuffix.ORDER.getSuffix())) {
                OrderCondition orderCondition = createOrderCondition(propertyName, value);
                orderCondition.setPrefix(getPrefix(qcoClass,propertyName));
                queryProperty.getOrders().add(orderCondition);
                continue;
            }
            QueryCondition tempCodition = convertForQueryCondition(propertyName, value);
            if (tempCodition != null) {
                tempCodition.setPrefix(getPrefix(qcoClass,propertyName));
                conditions.add(tempCodition);
            }
        }
        Map<String,Object> propertyMap =queryProperty.getPropertyMap();
        if( propertyMap != null ) {
            for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
                if (entry.getKey().endsWith(QcoSuffix.ORDER.getSuffix())) {
                    OrderCondition orderCondition = createOrderCondition(entry.getKey(), entry.getValue());
                    orderCondition.setPrefix(getPrefix(qcoClass,entry.getKey()));
                    queryProperty.getOrders().add(orderCondition);
                    continue;
                }
                QueryCondition codition = convertForQueryCondition(entry.getKey(), entry.getValue());
                if (codition != null) {
                    codition.setPrefix(getPrefix(qcoClass,entry.getKey()));
                    conditions.add(codition);
                }
            }
        }
        return conditions;
    }

    private static String getPrefix(Class cla,String pro)
    {
        com.hlg.fcf.annotation.Field field = FieldHelper.getFieldAnnotation(cla,pro);
        if( field != null ) {
            return field.prefix();
        }
        return null;
    }

    private static OrderCondition createOrderCondition(String propertyName,Object value)
    {
        propertyName = propertyName.substring(0, propertyName.length() - QcoSuffix.ORDER.getSuffix().length());
        OrderCondition orderCondition = new OrderCondition();
        orderCondition.setName(propertyName);
        orderCondition.setValue(String.valueOf(value));
        return orderCondition;
    }

}
