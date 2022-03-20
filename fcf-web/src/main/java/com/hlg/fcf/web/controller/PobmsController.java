package com.hlg.fcf.web.controller;

import com.alibaba.fastjson.JSON;
import com.hlg.fcf.annotation.PobmsAnnotation;
import com.hlg.fcf.api.common.PobmsApi;
import com.hlg.fcf.dao.BaseDao;
import com.hlg.fcf.model.basic.PobmsInfo;
import com.hlg.fcf.util.DbUtils;
import com.hlg.fcf.util.FieldHelper;
import com.hlg.fcf.util.SpringUtils;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 
 * @author wuwei
 * @date 2019年9月9日
 */
@RestController("frame.pobmsController")
@Api(value="框架PO表描述服务控制器",tags={"框架PO表描述服务控制器"})
public class PobmsController implements PobmsApi {

	@Override
	public List<PobmsInfo> getPobmss() {
		List<PobmsInfo> pobmsInfos = new ArrayList<>();
		Collection<BaseDao> list =  SpringUtils.getApplicationContext().getBeansOfType(BaseDao.class).values();

		for(BaseDao base : list){
			Class po = base.getModelClass();

			PobmsInfo pobmsInfo = new PobmsInfo();
			PobmsAnnotation[] pobmsAnnotation = (PobmsAnnotation[])base.getModelClass().getAnnotationsByType(PobmsAnnotation.class);

			if( pobmsAnnotation != null && pobmsAnnotation.length > 0 ) {
				pobmsInfo.setBm(DbUtils.getTableName(po));
				pobmsInfo.setBms(pobmsAnnotation[0].bms() + "");
				pobmsInfo.setZjm(DbUtils.getModelPkId(po));
				pobmsInfo.setYwbm(pobmsAnnotation[0].ywbm() + "");
				String sxms = pobmsAnnotation[0].sxms() + "";
				if(StringUtils.isBlank(sxms)){
					Map<String, String> map = getFieldDescMapping(po);
					pobmsInfo.setSxms(JSON.toJSONString(map));
				}else{
					pobmsInfo.setSxms(sxms);
				}

				pobmsInfos.add(pobmsInfo);
			}
		}

		return pobmsInfos;
	}

	/**
	 *  获取实体@Field注解description所映射关系
	 * @param clazz
	 * @return key: 属性名 </br>
	 *         value: description
	 */
	private static Map<String, String> getFieldDescMapping(Class<?> clazz) {
		Map<String, String> result = new HashMap<>();
		Set<Field> fileds = FieldHelper.getAllDeclaredField(clazz);
		for(Field field : fileds) {
			com.hlg.fcf.annotation.Field htcfField = field.getAnnotation(com.hlg.fcf.annotation.Field.class);
			if(htcfField == null) {
				continue;
			}
			String asFieldName = htcfField.description();
			if(org.apache.commons.lang.StringUtils.isNotBlank(asFieldName)) {
				result.put(field.getName(), asFieldName);
			}
		}
		return result;
	}
}
