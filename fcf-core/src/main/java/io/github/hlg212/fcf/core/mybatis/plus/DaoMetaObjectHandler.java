package  io.github.hlg212.fcf.core.mybatis.plus;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import  io.github.hlg212.fcf.dao.FillHandler;
import  io.github.hlg212.fcf.core.properties.AutoFillProperties;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;


public class DaoMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private List<FillHandler> fillHandlerList;

    @Autowired
    private AutoFillProperties autoFillProperties;

    @Override
    public void insertFill(MetaObject metaObject) {
        onFillHandle(FieldFill.INSERT.name(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        onFillHandle(FieldFill.UPDATE.name(), metaObject);
    }


    private void onFillHandle(String type, MetaObject metaObject) {
        MetaObject ometaObject = getMetaObject(metaObject);
        Set<String> propertySet = null;
        if( FieldFill.INSERT.name().equalsIgnoreCase(type) )
        {
            propertySet = autoFillProperties.getInsert();
        }
        else
        {
            propertySet = autoFillProperties.getUpdate();
        }
        propertySet.addAll(autoFillProperties.getInsertUpdate());
        propertySet.stream().filter(property -> {
                if ( ometaObject.hasSetter(property)) {
                    return true;
                }
            return false;
        }).forEach(property -> {
            fillHandlerList.stream().anyMatch(fillHandler -> {
                Object o = null;
                if (type.equalsIgnoreCase(FieldFill.INSERT.name())) {
                    o = fillHandler.inserFill(property, ometaObject.getOriginalObject(), ometaObject.getSetterType(property));
                } else {
                    o = fillHandler.updateFill(property, ometaObject.getOriginalObject(), ometaObject.getSetterType(property));
                }
                if (o != null) {
                    setFieldValByName(property, o, metaObject);
                    return true;
                }
                return false;
            });
        });
    }


    private  MetaObject getMetaObject(MetaObject metaObject)
    {
        if (metaObject.hasGetter("et")) {
            Object et = metaObject.getValue("et");
            if (et != null) {
                MetaObject etMeta = SystemMetaObject.forObject(et);
                return etMeta;
            }
        }
        return metaObject;
    }
}
