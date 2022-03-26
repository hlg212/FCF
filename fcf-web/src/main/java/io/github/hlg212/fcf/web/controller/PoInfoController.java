package  io.github.hlg212.fcf.web.controller;

import io.github.hlg212.fcf.annotation.PoInfoAnn;
import io.github.hlg212.fcf.api.common.PoInfoApi;
import io.github.hlg212.fcf.dao.BaseDao;
import io.github.hlg212.fcf.model.basic.PoInfo;
import io.github.hlg212.fcf.util.DbHelper;
import io.github.hlg212.fcf.util.FieldHelper;
import io.github.hlg212.fcf.util.JsonHelper;
import io.github.hlg212.fcf.util.SpringHelper;
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
@RestController("Frame.PoInfoController")
@Api(value="框架PO表描述服务控制器",tags={"框架PO表描述服务控制器"})
public class PoInfoController implements PoInfoApi {

	@Override
	public List<PoInfo> getPoInfos() {
		List<PoInfo> poInfos = new ArrayList<>();
		Collection<BaseDao> list =  SpringHelper.getApplicationContext().getBeansOfType(BaseDao.class).values();

		for(BaseDao base : list){
			Class po = base.getModelClass();

			PoInfo pobInfo = new PoInfo();
			PoInfoAnn[] pobmsAnn = (PoInfoAnn[])base.getModelClass().getAnnotationsByType(PoInfoAnn.class);

			if( pobmsAnn != null && pobmsAnn.length > 0 ) {
				pobInfo.setName(DbHelper.getTableName(po));
				pobInfo.setDesc(pobmsAnn[0].desc() + "");
				pobInfo.setPrimarykey(DbHelper.getModelPkId(po));
				pobInfo.setBusinessCode(pobmsAnn[0].businessCode() + "");
				String sxms = pobmsAnn[0].attributeDetails() + "";
				if(StringUtils.isBlank(sxms)){
					Map<String, String> map = getFieldDescMapping(po);
					pobInfo.setAttributeDetails(JsonHelper.toJson(map));
				}else{
					pobInfo.setAttributeDetails(sxms);
				}

				poInfos.add(pobInfo);
			}
		}

		return poInfos;
	}

	/**
	 *  获取实体@Field注解description所映射关系
	 * @param clazz
	 *   key: 属性名 </br>
	 *         value: description
	 */
	private static Map<String, String> getFieldDescMapping(Class<?> clazz) {
		Map<String, String> result = new HashMap<>();
		Set<Field> fileds = FieldHelper.getAllDeclaredField(clazz);
		for(Field field : fileds) {
			 io.github.hlg212.fcf.annotation.Field htcfField = field.getAnnotation( io.github.hlg212.fcf.annotation.Field.class);
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
