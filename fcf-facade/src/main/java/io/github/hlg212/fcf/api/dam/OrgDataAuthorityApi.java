package  io.github.hlg212.fcf.api.dam;

import  io.github.hlg212.fcf.api.Constants;
import  io.github.hlg212.fcf.service.DataAuthorityService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *
 * 机构数据权限接口
 *
 * @author huangligui
 * @date 2021年1月11日
 */

@FeignClient(contextId = Constants.ApiContextId.OrgDataAuthorityApi,name=Constants.ApiName.OrgDataAuthorityApi,path =Constants.ApiPath.OrgDataAuthorityApi,url =Constants.ApiUrl.OrgDataAuthorityApi)
@RequestMapping(Constants.ApiMapping.OrgDataAuthorityApi)
public interface OrgDataAuthorityApi extends DataAuthorityService<String> {

    @RequestMapping(value="/getAuthoritys", method = {RequestMethod.GET,RequestMethod.POST})
    public List<String> getAuthoritys(@RequestParam("uid") String uid, @RequestParam("param") String param);
}
