package  io.github.hlg212.fcf.api.dam;

import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.model.dam.IDataAuthorityPropertyCondition;
import  io.github.hlg212.fcf.model.dam.IDataAuthorityPropertyConditionValue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *
 * 数据权限条件接口
 *
 * @author huangligui
 * @date 2021年1月11日
 */
@FeignClient(contextId = Constants.ApiContextId.DataAuthorityPropertyConditionApi,name=Constants.ApiName.DataAuthorityPropertyConditionApi,path =Constants.ApiPath.DataAuthorityPropertyConditionApi,url =Constants.ApiUrl.DataAuthorityPropertyConditionApi)
@RequestMapping(Constants.ApiMapping.DataAuthorityPropertyConditionApi)
@ConditionalOnExpression("false")
public interface DataAuthorityPropertyConditionApi<T extends IDataAuthorityPropertyCondition>{


    @RequestMapping(value="/getConditions",method=RequestMethod.GET)
    List<T> getConditions(@RequestParam("configSetId") String configSetId);

    @RequestMapping(value="/getValue",method=RequestMethod.GET)
    <E extends IDataAuthorityPropertyConditionValue> E  getValue(@RequestParam("id") String Conditiony, @RequestParam("optype") String optype);

}
