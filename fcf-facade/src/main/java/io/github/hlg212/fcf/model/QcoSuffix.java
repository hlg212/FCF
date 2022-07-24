package  io.github.hlg212.fcf.model;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author huangligui
 * @date 2018年9月7日
 */
public enum 	QcoSuffix {
	
	/**
	 * !=
	 */
	NOT_EQ("NotEq", "!="),
	
	/**
	 * is
	 */
	IS("Is", "is"),

	/**
	 * like %xx% 
	 */
	LIKE("Like", "like"),
	
	/**
	 * like %xx
	 */
	LT_LIKE("LtLike", "LtLike"),
	
	/**
	 * like xx%
	 */
	RT_LIKE("RtLike", "RtLike"),
	
	/**
	 * in
	 */
	IN("In", "in"),
	
	/**
	 * in
	 */
	NOT_IN("NotIn", "not in"),
	
	/**
	 * <
	 */
	LT("Lt", "<"),
	
	/**
	 * <=
	 */
	LT_EQUAL("LtEq", "<="),
	
	/**
	 * >
	 */
	GT("Gt", ">"),
	
	/**
	 * >=
	 */
	GT_EQUAL("GtEq", ">="),
	
	/**
	 * Between and
	 */
	BETWEEN("Between", "between"),
	
	/**
	 * order
	 */
	ORDER("Order", "")
	;
	
	private String suffix;
	
	private String operation;
	
	private static Pattern suffixPatten;
	
	private static Pattern operationPatten;
	
	private static Map<String, QcoSuffix> map = new LinkedHashMap<>();
	
	static {
		StringBuffer suffixRegex = new StringBuffer();
		StringBuffer operationRegex = new StringBuffer();
		QcoSuffix[] values = QcoSuffix.values();
		Arrays.sort(values,new Comparator<QcoSuffix>() {

			@Override
			public int compare(QcoSuffix o1, QcoSuffix o2) {
				
				return o2.getSuffix().length() - o1.getSuffix().length();
			}
			
		});
		for(QcoSuffix qps : values) {
			suffixRegex.append(".+").append(qps.getSuffix()).append("$|");
			operationRegex.append(".+").append(qps.getOperation()).append("$|");
			map.put(qps.getSuffix(), qps);
		}
		suffixRegex.deleteCharAt(suffixRegex.length() - 1);
		operationRegex.deleteCharAt(operationRegex.length() - 1);
		suffixPatten = Pattern.compile(suffixRegex.toString());
		operationPatten = Pattern.compile(operationRegex.toString());
	}
	
	private QcoSuffix(String suffix, String operation) {
		this.suffix = suffix;
		this.operation = operation;
	}
	
	public String getSuffix() {
		return this.suffix;
	}
	
	public String getOperation() {
		return this.operation;
	}
	
	public static boolean containsSuffix(String name) {
		return suffixPatten.matcher(name).matches();
	}
	
	public static boolean containsOperation(String operation) {
		return operationPatten.matcher(operation).matches();
	}
	
	public static QcoSuffix get(String propertyName) {
		if(StringUtils.isBlank(propertyName) || !containsSuffix(propertyName)) {
			return null;
		}
		for(String key : map.keySet()) {
			if(propertyName.endsWith(key)) {
				return map.get(key);
			}
		}
		return null;
	}
}
