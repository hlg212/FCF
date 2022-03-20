package com.hlg.fcf.core.util;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TableHelper {

    private static Map<Class,Map<String,String>> modelColumnMap = new HashMap();
    private static Map<Class,Map<String,TableFieldInfo>> modelFieldInfoMap = new HashMap();

    public static String getColumn(Class model,String pro)
    {
        return modelColumnMap.get(model).get(pro);
    }
    public static String getWhereName(Class model,String pro)
    {
        return null;
    }
    public static String getKeyProperty(Class model)
    {
        return TableInfoHelper.getTableInfo(model).getKeyProperty();
    }
//    public static Class getPropertyType(Class model,String pro)
//    {
//        Map<String,TableFieldInfo> m = modelFieldInfoMap.get(model);
//        if( m != null )
//        {
//            TableFieldInfo info =  m.get(pro);
//            if( info != null )
//            {
//                return info.getPropertyType();
//            }
//        }
//        return null;
//    }

    public static String getTableName(Class model)
    {
        return TableInfoHelper.getTableInfo(model).getTableName();
    }


    public static void initTable(Class model)
    {
        Map<String,String> columnMap  = new LinkedHashMap();
        Map<String,TableFieldInfo> tableFieldInfonMap  = new LinkedHashMap();
        TableInfo info =  TableInfoHelper.getTableInfo(model);

        columnMap.put( info.getKeyProperty(),info.getKeyColumn() );
        info.getFieldList().forEach((tableFieldInfo -> {
            columnMap.put(tableFieldInfo.getProperty(),tableFieldInfo.getColumn());
            tableFieldInfonMap.put(tableFieldInfo.getProperty(),tableFieldInfo);

        }));

        Map<String,String> sqlWhere  = new LinkedHashMap();

        modelColumnMap.put(model,columnMap);
        modelFieldInfoMap.put(model,tableFieldInfonMap);
    }
}
