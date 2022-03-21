package io.hlg212.fcf.web.controller;

import io.hlg212.fcf.annotation.DataAuthorityConditional;
import io.hlg212.fcf.api.common.DataAuthorityConfigSetInfoApi;
import io.hlg212.fcf.dao.BaseDao;
import io.hlg212.fcf.model.dam.DataAuthorityConfigSetInfo;
import io.hlg212.fcf.util.FieldHelper;
import io.hlg212.fcf.util.SpringHelper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * 暴露出的数据权限信息接口
 * 使用dao进行权限控制
 *
 * @author huangligui
 * @date 2021年1月11日
 */
@DataAuthorityConditional
@RestController("frame.dataAuthorityInfoController")
@Api(value="框架数据权限信息描述控制器",tags={"框架数据权限信息描述控制器"})
public class DataAuthorityInfoController implements DataAuthorityConfigSetInfoApi {

	@Override
	public List<DataAuthorityConfigSetInfo> geInfo() {
		Map<String,BaseDao> daoMap =  SpringHelper.getApplicationContext().getBeansOfType(BaseDao.class);
		List<DataAuthorityConfigSetInfo> result = new ArrayList(daoMap.size());
		for( Map.Entry<String,BaseDao> entry : daoMap.entrySet() )
		{
			DataAuthorityConfigSetInfo info = new DataAuthorityConfigSetInfo();
			info.setCode(entry.getKey());
			Map<String, String> map = FieldHelper.getAllDeclaredFieldDesc(entry.getValue().getModelClass());
			info.setFiledInfo(map);
			result.add(info);
		}

		return result;
	}
}
