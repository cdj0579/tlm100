package com.unimas.common.xml;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.unimas.common.utils.xml.XmlLang;


/**
 * @中文类名:XmlUtils
 * @开发人员:rq:root
 * @开发日期:May 22, 2008
 * @功能说明:xml操作工具类
 */
public class XmlUtils {

	protected static final Logger log = Logger.getLogger(XmlUtils.class);

	/**
	 * 功能说明:读取xml文档
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static Document readXmlFile(String filename) throws Exception {
		Document doc = null;
		try {
            //log.debug("filename=" + filename);
            doc = XmlLang.getInstance().readXMLFile(filename);
		} catch (MalformedURLException e) {
			// 自动生成 catch 块
			throw new Exception("读取服务信息配置文件错误！",e);
		} catch (DocumentException e) {
			// 自动生成 catch 块
			throw new Exception("读取服务信息配置文件错误！",e);
		} catch (Exception e) {
			// 自动生成 catch 块
			throw e;
		}

		return doc;
	}

	
	public static Document readXmlFile(File file) throws Exception {
		Document doc = null;
		try {
            log.debug("filePath=" + file.getAbsolutePath());
            doc = XmlLang.getInstance().readXMLFile(file);
		} catch (MalformedURLException e) {
			// 自动生成 catch 块
			throw new Exception("读取服务信息配置文件错误！");
		} catch (DocumentException e) {
			// 自动生成 catch 块
			throw new Exception("读取服务信息配置文件错误！");
		} catch (Exception e) {
			// 自动生成 catch 块
			throw e;
		}

		return doc;
	}
	
	/**
	 * 功能说明:检查实例为doc的xml文档中节点ID属性值为id的节点
	 * 
	 * @param doc
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static Boolean checkXml(Document doc, String id) throws Exception {
		Boolean result = false;
		try {
			Element sub = doc.elementByID(id);// 读取节点ID属性值为id的节点
			if (sub != null) {// 以是否为null来判断节点是否存在
				result = true;
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	}

	/**
	 * 功能说明:保存Xml文件
	 * 
	 * @param filename
	 * @param doc
	 * @throws Exception
	 */
	public static int saveXmlFile(String filename, Document doc)
			throws Exception {
		int result = 0;
		try {
			XmlLang.getInstance().saveXMLFile(filename, doc);
			result = 1;
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	}

	public static Document createXml(String rootStr) throws Exception {
		Document doc = null;
		try {
			doc = DocumentHelper.createDocument();
			doc.addElement(rootStr);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return doc;
	}

	/**
	 * 功能说明:添加obj到以rootStr为根节点的XML文档中 obj的类名为rootStr的子节点,obj的属性字段为obj类名的子节点
	 * 
	 * @param root
	 * @param obj
	 * @return
	 */
	public static Document addString(String rootStr, String obj)
			throws Exception {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement(rootStr);
		try {
			Element rootsub = root.addElement(obj);
			// setElement(root, obj);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return doc;
	}

	/**
	 * 功能说明:添加OBJ到以rootStr为根节点的XML文档中 obj的类名为rootStr的子节点,obj的属性字段为obj类名节点的子节点
	 * 
	 * @param root
	 * @param obj
	 * @return
	 */
	public static Document addObject(String rootStr, Object obj)
			throws Exception {
		Document doc = DocumentHelper.createDocument();// 创建doc文档
		Element root = doc.addElement(rootStr);// 创建rootStr根节点
		try {
			setElement(root, obj);// 将obj添加到root根点上
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return doc;
	}

	/**
	 * 功能说明:添加OBJ到DOC 文档中 obj的类名做为节点添加到doc根节点下,obj的属性字段添加到obj类名节点下
	 * 
	 * @param doc
	 * @param obj
	 * @return
	 */
	public static Document addObject(Document doc, Object obj) throws Exception {
		Element root = doc.getRootElement();// 获取根节点
		try {
			setElement(root, obj);// 将obj添加到root根点上
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return doc;
	}

	/**
	 * 功能说明:在root节点上添加子节点key 值为value
	 * 
	 * @param root
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public static void setAttribute(Element root, String name, String attname,
			String value) throws Exception {
		try {
			Element keyel = root.element(name);
			if (keyel == null) {
				keyel = root.addElement(name);
			}
			keyel.addAttribute(attname, value);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * 功能说明:在root节点上添加子节点key 值为value
	 * 
	 * @param root
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public static void setElement(Element root, String key, String value)
			throws Exception {
		try {
			Element keyel = root.element(key);
			if (keyel == null) {
				keyel = root.addElement(key);
				keyel.addCDATA(value);
			} else {
				keyel.addCDATA(value);
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * 功能说明:更新doc文档中ID属性值为id的 obj节点
	 * 
	 * @param doc
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Document updateObject(Document doc, String id, Object obj)
			throws Exception {
		try {
			doc = delObject(doc, id);// 删除ID属性值等于id的节点
			doc = addObject(doc, obj);// 创建新的obj节点
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return doc;
	}

	/**
	 * 功能说明:删除doc中属性ID的值为id的节点
	 * 
	 * @param doc
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Document delObject(Document doc, String id) throws Exception {
		try {
			Element root = doc.getRootElement();
			Element sub = root.elementByID(id);
			root.remove(sub);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return doc;
	}

	/**
	 * 功能说明:在root节点上添加obj子节点 obj的类名为父节点,属性为子节点
	 * 
	 * @param root
	 * @param obj
	 * @throws Exception
	 */
	public static void setElement(Element root, Object obj) throws Exception {
		try {
			Element classname = root
					.addElement(getTableName(obj).toLowerCase());// 添加obj类名的节点
			classname.addAttribute("class", obj.getClass().getName());// 添加class属性
			Map<String, Object> map = getValuesMap(obj);// 获取obj类的属性(key)
			// 值(value)的map
			setElement(classname, map);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * 功能说明:在root节点上添加子节点 map
	 * 
	 * @param root
	 * @param map
	 * @throws Exception
	 */
	public static void setElement(Element root, Map map) throws Exception {
		try {
			String[] isfield = null;
			String[] befield = null;
			if (map.containsKey("IsAttributeFields")) {// 获取预定的需要添加属性的字段
				isfield = (String[]) map.get("IsAttributeFields");
			} else {
				isfield = new String[0];
			}
			if (map.containsKey("BeAttributeFields")) {// 获取预定的将成为其它字段的属性的字段
				befield = (String[]) map.get("BeAttributeFields");
			} else {
				befield = new String[0];
			}
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String field = (String) it.next();
				Object text = map.get(field);
				if (text instanceof List) {// 字段属性为list
					List list = (List) text;
					Iterator itlist = list.iterator();
					while (itlist.hasNext()) {
						Object subobj = (Object) itlist.next();
						setElement(root, subobj);// 递归调用添加subobj节点到roo上
					}
				} else {// 非list字段属性
					boolean f = true;
					for (int i = 0; i < befield.length; i++) {// 处理预定的将成为其它字段的属性的字段
						if (field.equalsIgnoreCase(befield[i])
								|| field.equalsIgnoreCase("IsAttributeFields")
								|| field.equalsIgnoreCase("BeAttributeFields")) {// 处理预定的属性
							if (isfield[i].equalsIgnoreCase("this")) { // this表示该字段将被添加为类名节点的属性
								root.addAttribute(befield[i].toUpperCase(),
										(String) map.get(befield[i]));// 添加
							}
							f = false;
							break;// 跳出for循环
						}
					}
					if (!f) {// 如果是预定字段将结束本次循环
						continue;// 结束本次while循环
					}
					Element key = root.addElement(field);
					key.addCDATA(text.toString());
					for (int i = 0; i < isfield.length; i++) {// 处理预定的需要添加属性的字段
						if (isfield[i].equalsIgnoreCase("this")) {// this表示该字段将被添加为类名节点的属性
							root.addAttribute(befield[i].toUpperCase(),
									(String) map.get(befield[i]));
						}
						if (field.equalsIgnoreCase(isfield[i])) {
							key.addAttribute(befield[i].toUpperCase(),
									(String) map.get(befield[i]));
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * 功能说明:根据id获取对象
	 * 
	 * @param doc
	 * @param id
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getObjectMap(Document doc, String id,
			Object cls) throws Exception {
		Map<String, String> obj = new HashMap<String, String>();
		try {
			Element root = doc.getRootElement();
			Element sub = root.elementByID(id);
			if (sub != null) {
				String[] array = getFieldsArray(cls);
				String[] isfield = getIsAttributeFields(cls);
				String[] befield = getBeAttributeFields(cls);
				for (int i = 0; i < array.length; i++) {
					Element field = null;
					String value = "";
					boolean f = false;
					for (int j = 0; j < befield.length; j++) {
						if (befield[j].equalsIgnoreCase(array[i])) {
							if (isfield[j].equalsIgnoreCase("this")) {
								value = sub.attributeValue(array[i]
										.toUpperCase());
							} else {
								field = sub.element(isfield[j]);
								value = field.attributeValue(array[i]
										.toUpperCase());
							}
							f = true;
						}
					}
					if (!f) {
						field = sub.element(array[i]);
						value = field.getText();
					}
					String name = "set"
							+ array[i].substring(0, 1).toUpperCase()
							+ array[i].substring(1);
					obj.put(array[i], value);
				}
			} else {
				throw new Exception("获取ID为:" + id + "的对象,不存在!可能已删除");
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return obj;
	}

	/**
	 * 功能说明:根据id获取对象
	 * 
	 * @param doc
	 * @param id
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static Object getObject(Document doc, String id, Object cls)
			throws Exception {
		Element root = doc.getRootElement();
		return getObject(root, id, cls);
	}

	/**
	 * 功能说明:根据id获取对象 支持LIST
	 * 
	 * @param doc
	 * @param id
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static Object getObject(Element root, String id, Object cls)
			throws Exception {
		Object obj = null;
		try {
			// Element root = doc.getRootElement();
			Element sub = root.elementByID(id);
			if (sub != null) {
				String[] array = getFieldsArray(cls);
				String[] types = getFieldsType(cls);
				obj = Class.forName(cls.getClass().getName()).newInstance(); // 创建一个新的实例
				String[] isfield = getIsAttributeFields(obj);
				String[] befield = getBeAttributeFields(obj);
				if (isfield == null || isfield.length == 0) {
					isfield = new String[0];
				}
				if (befield == null || befield.length == 0) {
					befield = new String[0];
				}
				for (int i = 0; i < array.length; i++) {
					if (types[i].equalsIgnoreCase("List")) {
						List resultlist = new ArrayList();
						// 还原LIST子节点
						List list = sub.elements(array[i]);//
						Iterator it = list.iterator();
						// while (it.hasNext()) {不要重复
						if (it.hasNext()) {
							Element nextsub = (Element) it.next();
							Object subobj = Class.forName(
									nextsub.attributeValue("class"))
									.newInstance();// 创建子类的实例
							List subsublist = getObject(sub, subobj);
							Iterator subsubit = subsublist.iterator();
							while (subsubit.hasNext()) {
								resultlist.add(subsubit.next());
							}
						}
						String name = "set"
								+ array[i].substring(0, 1).toUpperCase()
								+ array[i].substring(1);
						invokeMethod(obj, name, new Object[] { resultlist });
					} else {
						Element field = null;
						String value = "";
						boolean f = false;
						for (int j = 0; j < befield.length; j++) {
							if (befield[j].equalsIgnoreCase(array[i])) {
								if (isfield[j].equalsIgnoreCase("this")) {
									value = sub.attributeValue(array[i]
											.toUpperCase());
								} else {
									field = sub.element(isfield[j]);
									value = field.attributeValue(array[i]
											.toUpperCase());
								}
								f = true;
							}
						}
						if (!f) {
							field = sub.element(array[i]);
							if(null != field){
								value = field.getText();
							}else{
								value = "";
							}
						}
						String name = "set"
								+ array[i].substring(0, 1).toUpperCase()
								+ array[i].substring(1);
						if (types[i].equalsIgnoreCase("String;")) {// 处理String[]数组
							String[] values = value.split("\\|");
							invokeMethod(obj, name, new Object[] { values });
						} else {
							setObject(types[i], name, value, obj);
						}
					}
				}
			} else {
				throw new Exception(id + " 错误的ID");
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return obj;
	}

	public static List<Map<String, Object>> getObjectMap(Document doc,
			Object cls) throws Exception {
		Element root = doc.getRootElement();
		return getObjectMap(root, cls);
	}

	/**
	 * 功能说明:获取doc中所有cls类的节点转化为类 不支持LIST
	 * 
	 * @return
	 */
	public static List<Map<String, Object>> getObjectMap(Element root,
			Object cls) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			String[] array = getFieldsArray(cls);
			String[] isfield = getIsAttributeFields(cls);
			String[] befield = getBeAttributeFields(cls);
			List<Element> list = root.elements(getTableName(cls));
			if (list != null && !list.isEmpty()) {
				Iterator<Element> it = list.iterator();
				while (it.hasNext()) {
					Element element = (Element) it.next();
					Map<String, Object> map = new HashMap<String, Object>();// 创建一个新的实例
					for (int i = 0; i < array.length; i++) {
						Element field = element.element(array[i]);
						String value = "";
						boolean f = false;
						for (int j = 0; j < befield.length; j++) {
							if (befield[j].equalsIgnoreCase(array[i])) {
								if (isfield[j].equalsIgnoreCase("this")) {
									value = element.attributeValue(array[i]
											.toUpperCase());
								} else {
									field = element.element(isfield[j]);
									value = field.attributeValue(array[i]
											.toUpperCase());
								}
								f = true;
							}
						}
						if (!f) {
							if(null != field){
								value = field.getText();
							}else{
								value = "";
							}
						}
						map.put(array[i], value);
					}
					result.add(map);
				}
			} else {
				//throw new Exception("未查询到相关记录!");
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	}

	/**
	 * 功能说明:获取doc中所有cls类的节点转化为类 支持List
	 * 
	 * @return
	 */
	public static List<Object> getObject(Document doc, Object cls)
			throws Exception {
		Element root = doc.getRootElement();
		return getObject(root, cls);
	}

	/**
	 * 功能说明:获取doc中所有cls类的节点转化为类
	 * 
	 * @return
	 */
	public static List<Object> getObject(Element root, Object cls)
			throws Exception {
		List<Object> result = new ArrayList<Object>();
		try {
			String[] array = getFieldsArray(cls);
			String[] types = getFieldsType(cls);
			String[] isfield = getIsAttributeFields(cls);
			String[] befield = getBeAttributeFields(cls);
			List list = root.elements(getTableName(cls));
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Element element = (Element) it.next();
				Object obj = Class.forName(cls.getClass().getName())
						.newInstance(); // 创建一个新的实例
				for (int i = 0; i < array.length; i++) {
					if (types[i].equalsIgnoreCase("List")) {
						List resultlist = new ArrayList();
						Object subobj = null;
						// 还原LIST子节点
						List sublist = element.elements(array[i].toLowerCase());//
						Iterator subit = sublist.iterator();
						// 为了避重复 只取一个Element出来做为模版
						// while (subit.hasNext()) {
						if (subit.hasNext()) {
							Element nextsub = (Element) subit.next();
							subobj = Class.forName(
									nextsub.attributeValue("class"))
									.newInstance();// 创建子类的实例
							List subsublist = getObject(element, subobj);
							Iterator subsubit = subsublist.iterator();
							while (subsubit.hasNext()) {
								resultlist.add(subsubit.next());
							}
						}
						String name = "set"
								+ array[i].substring(0, 1).toUpperCase()
								+ array[i].substring(1);
						invokeMethod(obj, name, new Object[] { resultlist });
					} else {
						Element field = element.element(array[i]);
						String value = "";
						boolean f = false;
						for (int j = 0; j < befield.length; j++) {
							if (befield[j].equalsIgnoreCase(array[i])) {
								if (isfield[j].equalsIgnoreCase("this")) {
									value = element.attributeValue(array[i]
											.toUpperCase());
								} else {
									field = element.element(isfield[j]);
									if (field != null)
										value = field.attributeValue(array[i]
												.toUpperCase());
								}
								f = true;
							}
						}
						if (!f) {
							if (field != null)
								value = field.getText();
						}
						String name = "set"
								+ array[i].substring(0, 1).toUpperCase()
								+ array[i].substring(1);
						if (types[i].equalsIgnoreCase("String;")) {// 处理String[]数组
							String[] values = value.split("\\|");
							invokeMethod(obj, name, new Object[] { values });
						} else {
							setObject(types[i], name, value, obj);
						}
					}
				}
				result.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw e;
		}
		return result;
	}

	/**
	 * 功能说明:取类的所有属性值
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map getValuesMap(Object obj) throws Exception {
		Map result = new HashMap();
		try {
			String[] isfield = getIsAttributeFields(obj);
			String[] befield = getBeAttributeFields(obj);
			if (isfield != null && isfield.length > 0) {
				result.put("IsAttributeFields", isfield);
			}
			if (befield != null && befield.length > 0) {
				result.put("BeAttributeFields", befield);
			}
			String[] method = getMethodArray(obj);
			String[] fields = getFieldsArray(obj);
			String[] types = getFieldsType(obj);
			int tlength = types.length;
			int mlength = method.length;
			int flength = fields.length;
			for (int i = 0; i < mlength; i++) {
				int index = method[i].indexOf("get");
				if (index != -1) {
					for (int j = 0; j < tlength; j++) {
						if (method[i].substring(index + 3).toLowerCase().trim()
								.equals(fields[j].toLowerCase())) {
							if (types[j].equalsIgnoreCase("List")) {
								List tmp = new ArrayList();
								List list = new ArrayList();
								list = (List) invokeMethod(obj, method[i],
										new Object[] {});
								if (list != null) {
									Iterator it = list.iterator();
									while (it.hasNext()) {
										Object subobj = (Object) it.next();
										tmp.add(subobj);
									}
								} else {
									if (log.isInfoEnabled()) {
										log.info(method[i] + "方法返回为null");
									}
								}
								if (tmp != null && !tmp.isEmpty()) {
									result.put(fields[j], tmp);
								}
							} else {
								Object findarray = invokeMethod(obj, method[i],
										new Object[] {});
								String value = "";
								if (findarray instanceof String[]) {
									String[] temp = (String[]) findarray;
									for (int t = 0; t < temp.length; t++) {
										value += temp[t];
										if (t < temp.length - 1)
											value += "|";
									}
								} else {
									value = findarray == null ? "" : findarray
											.toString();
								}
								result.put(fields[j], value);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	}

	/**
	 * 功能说明:返回自动生成XML中生成其它字段属性的字段
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String[] getBeAttributeFields(Object obj) throws Exception {
		String[] result = null;
		try {
			result = (String[]) invokeMethod(obj, "queryBeAttribute",
					new Object[] {});
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	/**
	 * 功能说明:返回自动生成XML中需要生成属性的字段 this表示类名节点
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String[] getIsAttributeFields(Object obj) {
		String[] result = null;
		try {
			result = (String[]) invokeMethod(obj, "queryIsAttribute",
					new Object[] {});
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	public static void setObject(String type, String name, String value,
			Object obj) throws Exception {
		if (type.equals("Integer")) {
			invokeMethod(obj, name, new Object[] { Integer.valueOf(value) });
		} else if (type.equals("Long")) {
			invokeMethod(obj, name, new Object[] { Long.valueOf(value) });
		} else if (type.equals("String")) {
			invokeMethod(obj, name, new Object[] { String.valueOf(value) });
		} else if (type.equals("Float")) {
			invokeMethod(obj, name, new Object[] { Float.valueOf(value) });
		} else if (type.equals("Double")) {
			invokeMethod(obj, name, new Object[] { Double.valueOf(value) });
		} else if (type.equals("Boolean")) {
			invokeMethod(obj, name, new Object[] { Boolean.valueOf(value) });
		} else if (type.equals("Byte")) {
			invokeMethod(obj, name, new Object[] { Byte.valueOf(value) });
		}
	}

	/**
	 * 功能说明:执行类中的方法
	 * 
	 * @param owner
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethod(Object owner, String methodName,
			Object[] args) throws Exception {
		Class ownerClass = owner.getClass();
		Class[] argsClass = new Class[args.length]; // sb.append("'");
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		// return method.invoke(owner, args) == null ? "" : method.invoke(owner,
		// args);
		return method.invoke(owner, args);
	}

	/**
	 * 功能说明:取类obj所有属性的类型
	 * 
	 * @param obj
	 * @return
	 */
	public static String[] getFieldsType(Object obj) {
		Field[] field = obj.getClass().getDeclaredFields();
		int length = field.length;
		String[] result = new String[length];
		for (int i = 0; i < length; i++) {
			String temp = field[i].getType().getName();
			result[i] = temp.substring(temp.lastIndexOf(".") + 1);
		}
		return result;
	}

	/**
	 * 功能说明:根据映射来获得类所有属性名
	 * 
	 * @param obj
	 * @return 属性名数组
	 */
	public static String[] getFieldsArray(Object obj) {
		Field[] field = obj.getClass().getDeclaredFields();
		int length = field.length;
		String[] result = new String[length];
		for (int i = 0; i < length; i++) {
			result[i] = field[i].getName();
		}
		return result;
	}

	/**
	 * 功能说明:根据映射来获得类所有方法名
	 * 
	 * @param obj
	 * @return 方法名数组
	 */
	public static String[] getMethodArray(Object obj) {
		Method[] method = obj.getClass().getDeclaredMethods();
		int length = method.length;
		String[] result = new String[length];
		for (int i = 0; i < length; i++) {
			result[i] = method[i].getName();
		}
		return result;
	}

	/**
	 * 功能说明:根据CLASSNAME获得表名
	 * 
	 * @param classname
	 * @return
	 */
	public static String getTableName(Object obj) {
		return getTableName(obj.getClass().getName());
	}

	public static String getTableName(String classname) {
		return classname.substring(classname.lastIndexOf(".") + 1)
				.toLowerCase();
	}

	/*public static void main(String[] s) throws Exception {
		SearchCfg scg = new SearchCfg();
		String[] array = getFieldsArray(scg);
		String[] types = getFieldsType(scg);
		// for (int i = 0; i < array.length; i++) {
		// System.out.println(array[i]);
		// System.out.println(types[i]);
		// }
	}*/

}
