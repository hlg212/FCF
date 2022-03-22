package  io.github.hlg212.fcf.core.handler;

import  io.github.hlg212.fcf.dao.BaseDao;
import  io.github.hlg212.fcf.model.QueryParam;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CompositeDataAuthorityHandler implements  DataAuthorityHandler {

    @Autowired(required = false)
    private List<DataAuthorityHandler> dataAuthorityHandlers;

    @Override
    public void onAdd(Object o, BaseDao dao) {
        if( dataAuthorityHandlers == null )
            return;
       for(DataAuthorityHandler dataAuthorityHandlers : dataAuthorityHandlers)
       {
           dataAuthorityHandlers.onAdd(o,dao);
       }
    }

    @Override
    public void onUpdate(Object o, BaseDao dao) {
        if( dataAuthorityHandlers == null )
            return;
        for(DataAuthorityHandler dataAuthorityHandlers : dataAuthorityHandlers)
        {
            dataAuthorityHandlers.onUpdate(o,dao);
        }
    }

    @Override
    public void onDelete(Object o, BaseDao dao) {
        if( dataAuthorityHandlers == null )
            return;
        for(DataAuthorityHandler dataAuthorityHandlers : dataAuthorityHandlers)
        {
            dataAuthorityHandlers.onDelete(o,dao);
        }
    }

    @Override
    public void onQuery(QueryParam queryParam,BaseDao dao) {
        if( dataAuthorityHandlers == null )
            return;
        for(DataAuthorityHandler dataAuthorityHandlers : dataAuthorityHandlers)
        {
            dataAuthorityHandlers.onQuery(queryParam,dao);
        }
    }

}
