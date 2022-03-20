package com.hlg.fcf.web.controller;

import com.hlg.fcf.annotation.DataAuthorityConditional;
import com.hlg.fcf.api.common.DataAuthorityConfigSetInfoApi;
import com.hlg.fcf.dao.BaseDao;
import com.hlg.fcf.model.dam.DataAuthorityConfigSetInfo;
import com.hlg.fcf.util.FieldHelper;
import com.hlg.fcf.util.SpringUtils;
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
		Map<String,BaseDao> daoMap =  SpringUtils.getApplicationContext().getBeansOfType(BaseDao.class);
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
