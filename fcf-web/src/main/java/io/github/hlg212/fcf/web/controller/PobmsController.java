package  io.github.hlg212.fcf.web.controller;

import com.alibaba.fastjson.JSON;
import  io.github.hlg212.fcf.annotation.PobmsAnnotation;
import  io.github.hlg212.fcf.api.common.PobmsApi;
import  io.github.hlg212.fcf.dao.BaseDao;
import  io.github.hlg212.fcf.model.basic.PobmsInfo;
import  io.github.hlg212.fcf.util.DbHelper;
import  io.github.hlg212.fcf.util.FieldHelper;
import  io.github.hlg212.fcf.util.SpringHelper;
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
		Collection<BaseDao> list =  SpringHelper.getApplicationContext().getBeansOfType(BaseDao.class).values();

		for(BaseDao base : list){
			Class po = base.getModelClass();

			PobmsInfo pobmsInfo = new PobmsInfo();
			PobmsAnnotation[] pobmsAnnotation = (PobmsAnnotation[])base.getModelClass().getAnnotationsByType(PobmsAnnotation.class);

			if( pobmsAnnotation != null && pobmsAnnotation.length > 0 ) {
				pobmsInfo.setBm(DbHelper.getTableName(po));
				pobmsInfo.setBms(pobmsAnnotation[0].bms() + "");
				pobmsInfo.setZjm(DbHelper.getModelPkId(po));
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
