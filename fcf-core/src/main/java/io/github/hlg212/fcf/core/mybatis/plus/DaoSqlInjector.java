package  io.github.hlg212.fcf.core.mybatis.plus;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import  io.github.hlg212.fcf.annotation.Table;
import  io.github.hlg212.fcf.core.cache.PkIdCache;
import  io.github.hlg212.fcf.core.properties.AutoFillProperties;
import  io.github.hlg212.fcf.core.util.TableHelper;
import  io.github.hlg212.fcf.exception.BaseException;
import  io.github.hlg212.fcf.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.ResultMap;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 *  plus 集成增强
 *
 * ClassName: DaoSqlInjector
 * date: 2019年7月10日 上午8:50:16
 *
 * @author huangligui
 */
@Slf4j
public class DaoSqlInjector extends DefaultSqlInjector {

    private PkIdCache pkIdCache = PkIdCache.getInstance();
    @Autowired
    private AutoFillProperties autoFillProperties;

    static Field fieldFill = FieldUtils.getField(TableFieldInfo.class,"fieldFill",true);


    @Override
    public void inspectInject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass) {
        Class<?> modelClass = this.extractModelClass(mapperClass);
        if( modelClass == null )
        {
            return;
        }
        TableInfo tableInfo = TableInfoHelper.initTableInfo(builderAssistant, modelClass);
        TableHelper.initTable(modelClass);
        String tableName = getTableName(modelClass);
        if( StringUtils.isNotEmpty(tableName) )
        {
            tableInfo.setTableName(tableName);
        }
        if(StringUtils.isEmpty( tableInfo.getKeyProperty() ) )
        {
            log.warn("[{}]找不到主键，尝试从其它途径获取；",tableInfo.getTableName());
            String key = getKeyProperty(modelClass);
            if( key != null )
            {
                log.warn("[{}]设置主键[{}]",tableInfo.getTableName(),key);

                Iterator iterator = tableInfo.getFieldList().iterator();
                while (iterator.hasNext())
                {
                    TableFieldInfo fieldInfo =(TableFieldInfo) iterator.next();
                    if( key.equalsIgnoreCase(fieldInfo.getProperty()) )
                    {
                        iterator.remove();
                        break;
                    }
                }
                tableInfo.setKeyRelated(TableInfoHelper.checkRelated(tableInfo.isUnderCamel(), key, key)).setIdType(tableInfo.getConfiguration().getGlobalConfig().getDbConfig().getIdType()).setKeyColumn(key).setKeyProperty(key);
            }
            else
            {
                log.warn("[{}]找不到主键，判断为试图，请不要使用主键相关方法!",tableInfo.getTableName());
            }
          }
        Class bo = ModelHelper.findModel(modelClass,ModelHelper.ModelType.BO);
        if( bo != null )
        {
            if( !modelClass.isAssignableFrom(bo) )
            {
                log.warn(" [{}] 没有正确的继承 [{}],放弃使用Bo进行转换。请确定定义的使用方式!",bo.getSimpleName(),modelClass.getSimpleName());
            }
           else {
                ResultMap inlineResultMap = (new ResultMap.Builder(builderAssistant.getConfiguration(), bo.getName(), bo, new ArrayList(), (Boolean) null)).build();
                //builderAssistant.getConfiguration().addResultMap(inlineResultMap);
                if (!builderAssistant.getConfiguration().hasResultMap(inlineResultMap.getId())) {
                    builderAssistant.getConfiguration().addResultMap(inlineResultMap);
                }
                tableInfo.setResultMap(bo.getName());
            }
        }
        fillPropertys(tableInfo);
        super.inspectInject(builderAssistant, mapperClass);
    }




    private void fillPropertys(TableInfo tableInfo)
    {
        Iterator iterator = tableInfo.getFieldList().iterator();
        Set<String> insert = autoFillProperties.getInsert();
        Set<String> update  = autoFillProperties.getUpdate();
        Set<String> insertUpdate = autoFillProperties.getInsertUpdate();

        while (iterator.hasNext())
        {
            TableFieldInfo fieldInfo = (TableFieldInfo) iterator.next();

            fillPropertys(fieldInfo,insert,FieldFill.INSERT);
            fillPropertys(fieldInfo,update,FieldFill.UPDATE);
            fillPropertys(fieldInfo,insertUpdate,FieldFill.INSERT_UPDATE);

        }
    }

    private void fillPropertys(TableFieldInfo fieldInfo,Set<String> fills,FieldFill fill)
    {
        boolean has  = fills.contains(fieldInfo.getProperty());
        if( has )
        {
            try{
                fieldFill.set(fieldInfo,fill);
            }catch (Exception e)
            {
                log.warn("设置自动填充错误！",e);
            }
        }
    }



    private String getKeyProperty(Class modelClass)
    {
       return getPrimeryKeyField(modelClass);
    }


    private String getTableName(Class<?> modelClass)
    {
        Table t = modelClass.getAnnotation(Table.class);
        if( t != null )
        {
            return EnvironmentHelper.resolvePlaceholders(t.value());

            //return t.value();
        }
        return null;
    }


    private String getPrimeryKeyField(Class clazz ) {
        String className = clazz.getName();
        String id = pkIdCache.get(className);
        if(id != null) {
            return id;
        }
        id = DbHelper.getModelPkId(clazz);
        if(id != null) {
            pkIdCache.put(className, id);
            return id;
        }
        SqlSessionTemplate sqlSessionTemplate = SpringHelper.getBean(SqlSessionTemplate.class);
        if( sqlSessionTemplate != null ) {
            Connection connection = null;
            try {
                connection = sqlSessionTemplate.getConnection();
                DatabaseMetaData metaData = connection.getMetaData();
                String tableName = TableHelper.getTableName(clazz);
                ResultSet resultSet = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
                boolean isFlag = resultSet.next();
                if (!isFlag) {
                    ExceptionHelper.throwServerException(tableName + "表中未设主键，请设置主键或在对应实体中用注解@PkId指定主键列");
                }
                String columnName = resultSet.getString("COLUMN_NAME");
                id = getFieldNameByColumn(clazz, columnName);
                pkIdCache.put(className, id);
                return id;
            } catch (BaseException e) {
                throw e;
            } catch (Exception e) {
                log.error("通过获取db主键反推对应实体名出现异常! T:{}", clazz, e);
                ExceptionHelper.throwServerException("通过获取db主键反推对应实体名出现异常!", e);
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }
        return null;
    }


    /**
     * 根据列名获取实体对应的属性名称
     *
     * @param clazz
     * @param columnName
     *
     */
    private  String getFieldNameByColumn(Class<?> clazz, String columnName) {
        if(org.apache.commons.lang.StringUtils.isBlank(columnName)) {
            return null;
        }
        Set<Field> fields = FieldHelper.getAllDeclaredField(clazz);
        for(Field field : fields) {
            String tempColumnName = TableHelper.getColumn(clazz, field.getName());
            if(tempColumnName.equalsIgnoreCase(columnName)) {
                return field.getName();
            }
        }
        ExceptionHelper.throwServerException("未找到"+columnName+"对应的属性");
        return null;
    }
}
