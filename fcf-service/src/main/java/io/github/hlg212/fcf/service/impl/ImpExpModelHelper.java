package  io.github.hlg212.fcf.service.impl;

import  io.github.hlg212.fcf.model.ImpExpModel;
import  io.github.hlg212.fcf.util.AppContextHelper;
import  io.github.hlg212.fcf.util.ExceptionHelper;
import  io.github.hlg212.fcf.util.ModelHelper;
import  io.github.hlg212.fcf.util.ThreadLocalHelper;

import java.util.Map;

class ImpExpModelHelper {

    static public ImpExpModel getImpExpModel(Class modelClass) {
        String cacheName = modelClass.getSimpleName() + "ImpExpModel";
        ImpExpModel ieo = ThreadLocalHelper.get(cacheName);
        if (ieo == null) {
            Class iecls = findImpExpModelClass(modelClass);

            try {
                ieo = (ImpExpModel) iecls.newInstance();
            } catch (Exception e) {
                ExceptionHelper.throwServerException(e);
            }
            init(ieo,modelClass);
        }
        ThreadLocalHelper.set(cacheName, ieo);
        return ieo;
    }

    static private void init(ImpExpModel ieo, Class modelClass) {
        Class entityClass = ieo.getEntityClass();
        if (entityClass == null) {
            entityClass = modelClass;
            ieo.setEntityClass(entityClass);
        }
        Map fildMap = null;
        if (ieo.getProps() == null || ieo.getProps().length == 0) {
            fildMap = ModelHelper.getModelFieldsDes(entityClass);
        } else {
            fildMap = ModelHelper.getModelFieldsDes(entityClass, ieo.getProps());
        }

        if (ieo.getProps() == null || ieo.getProps().length == 0) {
            ieo.setProps((String[]) fildMap.keySet().toArray(new String[0]));
        }

        if (ieo.getHeaders() == null || ieo.getHeaders().length == 0) {
            ieo.setHeaders(initHeaders(ieo.getProps(), fildMap));
        }
    }


    static public String getDictKey(String dictStr) {
        int index = dictStr.indexOf("#");
        if (index != -1) {
            return dictStr.substring(index + 1);
        }
        return dictStr;
    }

    static public String getDictApp(String dictStr) {
        String dictKeys[] = dictStr.split("#");
        if (dictKeys.length > 1) {
            return dictKeys[0];
        }
        return AppContextHelper.getAppCode();
    }

   static  private String[] initHeaders(String[] props, Map fileMap) {
        String[] headers = new String[props.length];
        for (int i = 0; i < props.length; i++) {
            headers[i] = (String) fileMap.get(props[i]);
        }
        return headers;
    }

   static private Class findImpExpModelClass(Class modelClass) {
        Class ieoClass = null;
        if (modelClass != null) {
            ieoClass = ModelHelper.findModel(modelClass, "Ieo");
        }
        if (ieoClass == null) {
            ieoClass = ImpExpModel.class;
        }
        return ieoClass;
    }
}
