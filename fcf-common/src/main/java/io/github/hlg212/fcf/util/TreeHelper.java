package  io.github.hlg212.fcf.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;


/**
 * 树转换工具
 * <p>
 * 帮助将一个集合list,转成树结构
 *
 *
 * @author huangligui
 * @date 2018-11-19 上午10:36:40
 */
@Slf4j
public class TreeHelper {

	/**
	 *
	 * 将集合转换成树集合
	 *
	 * 默认使用 id,pid,childs
	 *
	 * @param list
	 */
	public static <T>  List<T> buildTree(List<T> list)
	{
		try
		{
			return buildTree(list,"id","pid","children");
		}catch(Exception e)
		{
			ExceptionHelper.throwServerException(e);
		}
		return null;
	}


	/**
	 * 
	 * 构建树
	 * 
	 * @param list
	 * @param nodeName
	 * @param parentName
	 * @param childListName
	 */
	public static <T> List<T> buildTree(List<T> list, String nodeName, String parentName, String childListName){
		NodeParam nodeParam = new NodeParam(nodeName, parentName, childListName);
		if (list.size()>0) {
			try {
				return createTree(list, nodeParam);
			} catch (Exception e) {
				log.error("生成树失败！",e);
				ExceptionHelper.throwServerException(e);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * 
	 * 返回树形结构
	 * 
	 * @param list
	 * @param nodeParam
	 *
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static <T> List<T> createTree(List<T> list, NodeParam nodeParam) throws Exception {
		List<T> resuleList = new ArrayList<T>();
		// parent 对应 child集合
		Map<String, List<T>> parentMap = new HashMap<String, List<T>>();
		// id 对应 对象
		Map<String, T> nodeMap = new HashMap<String, T>();
		for (T t : list) {
			String node =  (String) getValue(t, nodeParam.getNodeName());
			nodeMap.put(node, t);
		}
		for (T t : list) {
			String parentNode = (String) getValue(t, nodeParam.getParentName());
			T parant = null;
			if( StringUtils.isNotEmpty(parentNode) )
			{
				parant = nodeMap.get(parentNode);
			}
			if( parant != null ) {
				Collection<T> childList = (Collection) getValue(parant, nodeParam.getChildListName());
				if (childList == null) {
					childList = new ArrayList<>();
					setValue(parant, nodeParam.getChildListName(), childList);
				}
				childList.add(t);
			}
			else
			{
				resuleList.add(t);
			}
		}
		return resuleList;
	}

	/**
	 * 设置对应属性值
	 * 
	 * @param nodeCild
	 * @param property
	 * @param o
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static <T> void setValue(T nodeCild, String property, Object o) throws Exception {
		PropertyUtils.setProperty(nodeCild, property, o);
	}

	/**
	 * 得到对应属性值
	 * 
	 * @param node
	 * @param property
	 *
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static <T> Object  getValue(T node, String property) throws Exception {
		return PropertyUtils.getProperty(node, property);
	}
	
	
	public static class NodeParam {
		private String nodeName;
		private String parentName;
		private String childListName;
		private String isLeaf="isLeaf";
		private String level;
		
		/** 
		 * Creates a new instance of NodeParam. 
		 *  
		 */
		public NodeParam() {
			super();
		}
		/** 
		 * Creates a new instance of NodeParam. 
		 * 
		 * @param nodeName
		 * @param parentName
		 * @param childListName 
		 */
		public NodeParam(String nodeName, String parentName, String childListName) {
			super();
			this.nodeName = nodeName;
			this.parentName = parentName;
			this.childListName = childListName;
		}
		
		
		/** 
		 * Creates a new instance of NodeParam. 
		 * 
		 * @param nodeName
		 * @param parentName
		 * @param childListName
		 * @param isLeaf 
		 */
		public NodeParam(String nodeName, String parentName, String childListName, String isLeaf) {
			super();
			this.nodeName = nodeName;
			this.parentName = parentName;
			this.childListName = childListName;
			this.isLeaf = isLeaf;
		}
		/**
		 *   the nodeName
		 */
		public String getNodeName() {
			return nodeName;
		}
		/**
		 * @param nodeName the nodeName to set
		 */
		public void setNodeName(String nodeName) {
			this.nodeName = nodeName;
		}
		/**
		 *   the parentName
		 */
		public String getParentName() {
			return parentName;
		}
		/**
		 * @param parentName the parentName to set
		 */
		public void setParentName(String parentName) {
			this.parentName = parentName;
		}
		/**
		 *   the childListName
		 */
		public String getChildListName() {
			return childListName;
		}
		/**
		 * @param childListName the childListName to set
		 */
		public void setChildListName(String childListName) {
			this.childListName = childListName;
		}
		/**
		 *   the isLeaf
		 */
		public String getIsLeaf() {
			return isLeaf;
		}
		/**
		 * @param isLeaf the isLeaf to set
		 */
		public void setIsLeaf(String isLeaf) {
			this.isLeaf = isLeaf;
		}
		/**
		 *   the level
		 */
		public String getLevel() {
			return level;
		}
		/**
		 * @param level the level to set
		 */
		public void setLevel(String level) {
			this.level = level;
		}
	}

}