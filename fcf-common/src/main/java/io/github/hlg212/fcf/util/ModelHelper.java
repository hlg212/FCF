package  io.github.hlg212.fcf.util;

import  io.github.hlg212.fcf.annotation.Fields;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;


/**
 * 实体模型帮助工具
 *
 * @author huangligui
 * @date 2020年5月30日
 */
public class ModelHelper {

    public static Class findModel(Class mod,ModelType modelType)
    {
        return findModel( mod, modelType.name());
    }

    public static Class findModel(Class mod,String modelType)
    {
        String clsName = mod.getName();
        ModelType mtype = getModelType(mod);
        ModelType findModelType =  ModelType.valueOf(modelType.toUpperCase());
        if( mtype  != null  )
        {
            clsName = clsName.replace("."+mtype.packageName+".", "."+findModelType.packageName+".");

            if( findModelType == ModelType.PO ){
                clsName =  clsName.substring(0, clsName.length() - mtype.modelName.length());
            }
            else {
                clsName = clsName.substring(0, clsName.length() - mtype.modelName.length()) + findModelType.modelName;
            }
            try {
                return Class.forName(clsName);
            } catch (ClassNotFoundException e) {
            }
        }

        return null;
    }

    public static Map<String, String> getModelFieldsDes(Class<?> modelClass)
    {
        Map<String,String> result = new HashMap<>(10);
        Fields fields = modelClass.getAnnotation(Fields.class);
        List< io.github.hlg212.fcf.annotation.Field> fieldArr = new ArrayList();
        if(fields != null ) {
            fieldArr = Arrays.asList(fields.value());
        }
        if(fieldArr == null || fieldArr.size() == 0 )
        {
            PropertyDescriptor[] pds =  BeanUtils.getPropertyDescriptors(modelClass);
            for( PropertyDescriptor pd : pds )
            {
                String fname = pd.getShortDescription();
                Field field = FieldUtils.getField(modelClass,fname,true);
                if( field != null )
                {
                     io.github.hlg212.fcf.annotation.Field htcfField = field.getAnnotation( io.github.hlg212.fcf.annotation.Field.class);
                    if( htcfField != null ) {
                        result.put(fname, htcfField.description());
                    }
                }

            }
        }
        return result;
    }

    public static Map<String, String> getModelFieldsDes(Class<?> modelClass,String[] props)
    {
        Map map = new HashMap();
        for( String prop : props )
        {
            map.put(prop,getPropFiledDesc(modelClass,prop));
        }
        return map;
    }

    private static String getPropFiledDesc(Class<?> modelClass,String prop)
    {
        String p = null;
        String child = null;
        int index = prop.indexOf(".");
        if( index != -1 )
        {
            child = prop.substring(index+1);
            p = prop.substring(0,index);
        }else
        {
            p = prop;
        }
        PropertyDescriptor pd =  BeanUtils.getPropertyDescriptor(modelClass,p);
        if( pd != null )
        {
            String fname = pd.getShortDescription();
            Field field = FieldUtils.getField(modelClass,fname,true);
            if( field != null )
            {
                if( child != null )
                {
                    return getPropFiledDesc(field.getType(),child);
                }
                else{
                     io.github.hlg212.fcf.annotation.Field htcfField = field.getAnnotation( io.github.hlg212.fcf.annotation.Field.class);
                    if( htcfField != null ) {
                        return htcfField.description();
                    }
                }

            }
        }
        return null;
    }




    private static ModelType getModelType(Class modelCls) {
        String clsName = modelCls.getName();
        for (ModelType t : ModelType.values()) {
            if (clsName.endsWith(t.modelName)) {
                return t;
            }
        }
        if (modelCls.getName().indexOf("."+ModelType.PO.packageName+".") != -1)
        {
            return ModelType.PO;
        }

        return null;
    }

    public enum ModelType{
        BO("Bo"),PO("","po"),IEO("Ieo"),QCO("Qco");

        private String packageName;
        private String modelName;
        ModelType(String name) {
            this(name,name.toLowerCase());
        }

        ModelType(String name,String packageName) {
            this.packageName = packageName;
            this.modelName = name;
        }
    }

}
