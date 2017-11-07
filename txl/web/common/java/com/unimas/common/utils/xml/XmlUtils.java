package com.unimas.common.utils.xml;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @中文类名:XmlUtils
 * @开发人员:rq:root
 * @开发日期:May 22, 2008
 * @功能说明:xml操作工具类
 */
public class XmlUtils {

	protected static final Logger log = Logger.getLogger(XmlUtils.class);
	
	private static boolean use_CData = true;
	
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
	public static int saveXmlFile(String filename, Document doc, String encoding)
			throws Exception {
		int result = 0;
		try {
			XmlLang.getInstance().saveXMLFile(filename, doc, encoding);
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
	 * 功能说明:添加OBJ到XML文档中<br/>
	 *    如果rootStr为NULL或"",则默认添加到root根节点
	 * @param rootStr      obj应射的节点
	 * @param obj          OBJ对象
	 * @param hasRootNode  是否添加默认的root根节点
	 * @return
	 * @throws Exception 
	 */
	public static Document parseDocument(String rootStr, Object obj, boolean hasRootNode) throws Exception{
		return parseDocument(rootStr, obj, hasRootNode, true);
	}
	
	/**
	 * 功能说明:添加OBJ到XML文档中<br/>
	 *    如果rootStr为NULL或"",则默认添加到root根节点
	 * @param rootStr      obj应射的节点
	 * @param obj          OBJ对象
	 * @param hasRootNode  是否添加默认的root根节点
	 * @return
	 */
	public static Document parseDocument(String rootStr, Object obj, boolean hasRootNode, boolean useCData)
			throws Exception {
		use_CData = useCData;
		if(rootStr == null || rootStr.trim().equals("")) {
			rootStr = "root";
			hasRootNode = true;
		}
		if(hasRootNode){
			return parseDocumentHasRoot(rootStr, obj, useCData);
		} else {
			Document doc = DocumentHelper.createDocument();// 创建doc文档
			Element root = doc.addElement(rootStr);
			try {
				parseElement(root, obj);// 将obj应射到节点上
			} catch (Exception e) {
				log.error(e);
				throw e;
			}
			return doc;
		}
	}
	
	/**
	 * 功能说明:添加OBJ到根节点为root的XML文档中 ，obj是root下rootStr的子节点<br/>
	 *    如果rootStr为NULL或"root",则obj就是root根节点
	 * @param rootStr    obj应射的节点
	 * @param obj        OBJ对象
	 * @return
	 */
	public static Document parseDocument(String rootStr, Object obj)
			throws Exception {
		return parseDocument(rootStr, obj, true);
	}

	/**
	 * 功能说明:添加OBJ到根节点为root的XML文档中 ，obj是root下rootStr的子节点<br/>
	 *    如果rootStr为NULL或"root",则obj就是root根节点
	 * @param rootStr    obj应射的节点
	 * @param obj        OBJ对象
	 * @return
	 */
	public static Document parseDocumentHasRoot(String rootStr, Object obj, boolean useCData)
			throws Exception {
		use_CData = useCData;
		Document doc = DocumentHelper.createDocument();// 创建doc文档
		Element root = doc.addElement("root");
		if(rootStr != null && !"root".equals(rootStr)){
			parseDocument(root, rootStr, obj);
		} else {
			try {
				parseElement(root, obj);// 将obj应射到节点上
			} catch (Exception e) {
				log.error(e);
				throw e;
			}
		}
		return doc;
	}
	
	/**
	 * 功能说明:添加OBJ到指定节点中
	 * @param p          父节点
	 * @param fname      要添加的节点名称
	 * @param obj        
	 * @throws Exception
	 */
	public static void parseDocument(Element p, String fname, Object obj)
			throws Exception {
		if(fname != null && !"".equals(fname) && p != null){
			Element el = p.addElement(fname);
			try {
				parseElement(el, obj);// 将obj应射到节点上
			} catch (Exception e) {
				log.error(e);
				throw e;
			}
		}
	}

	/**
	 * 功能说明: 将obj应射到指定节点上
	 * 
	 * @param el      指定节点
	 * @param obj       OBJ对象
	 * @throws Exception
	 */
	public static void parseElement(Element el, Object obj) throws Exception {
		if(obj instanceof List){
			setElements(el, obj, false);
		} else if(obj.getClass().isArray()){
			setElements(el, obj, true);
		} else {
			setElement(el, obj);
		}
	}
	
	/**
	 * 功能说明: 将数组或集合中的元素作为子节点应射到指定节点上
	 * 
	 * @param p         指定节点
	 * @param obj       OBJ对象
	 * @throws Exception
	 */
	private static void parseChildElements(Element p, Object obj, String fname) throws Exception {
		if(obj instanceof List){
			setElements(p, obj, false);
		} else if(obj.getClass().isArray()){
			setElements(p, obj, true);
		} else {
			parseElement(p, obj);
		}
	}
	
	/**
	 * 将集合对象应射到指定节点下
	 * @param parent     指定节点
	 * @param obj        集合对象，只能是数组或List
	 * @throws Exception
	 */
	private static void setElements(Element parent, Object obj, boolean isArray) throws Exception {
		if(parent == null) return;
		Object[] objs = null;
		List<?> list = null;
		int l = 0;
		if(isArray){
			objs = (Object[])obj;
			l = objs.length;
		} else {
			list = (List<?>)obj;
			l = list.size();
		}
		for(int i=0;i<l;i++){
			Object sub = null;
			if(isArray){
				sub = objs[i];
			} else {
				list = (List<?>)obj;
				sub = list.get(i);
			}
			if(sub != null) {
				Element subEl = parent.addElement(getElementName(sub));
				parseElement(subEl, sub);
			}
		}
	}
	
	/**
	 * 功能说明: 将对象应射到指定节点的属性上
	 * @param el            指定节点
	 * @param obj           对象
	 * @param attributeName 读取指定属性值,为NULL时el是当前节点
	 */
	private static void setAttribute(Element el, Object obj, String attributeName){
		if(obj == null) return;
		el.addAttribute(attributeName, obj.toString());
	}
	
	/**
	 * 功能说明: 将非数组对象应射到指定节点上
	 * @param el     指定节点
	 * @param obj        非数组对象
	 * @throws Exception
	 */
	private static void setElement(Element el, Object obj) throws Exception {
		if(obj instanceof ObjXml){
			Class<?> c = obj.getClass();
			List<Field> fields = getFieldsFromClass(c);
			if(fields != null && fields.size() > 0){
				for(Field field : fields){
					field.setAccessible(true);
					Object value = field.get(obj);
					field.setAccessible(false);
					if(value != null){
						String fname = getElementName(field, value.getClass());
						if(field.isAnnotationPresent(Attribute.class)){
							setAttribute(el, value, fname);
						} else {
							if(field.isAnnotationPresent(ChildNodes.class)){
								parseChildElements(el, value, fname);
							} else {
								Element fieldEl = el.addElement(fname);
								parseElement(fieldEl, value);
							}
						}
					}
				}
			} else {
				setData(el, "");
			}
		} else if(obj instanceof Map){
			Map<?,?> m = (Map<?,?>)obj;
			Set<?> keySet = m.keySet();
			if(keySet.size() > 0){
				for(Object key : keySet){
					Object value = m.get(key);
					if(value != null){
						Element fieldEl = el.addElement(key.toString());
						parseElement(fieldEl, value);
					}
				}
			} else {
				setData(el, "");
			}
		} else {
			setData(el, obj.toString());
		}
	}
	
	/**
	 * 将DOM对象解析成JavaBean
	 * @param <T>
	 * @param doc          DOM对象
	 * @param tr           JavaBean类型
	 * @param nodeName     以指定的结点做为根结点，为null时表示有默认根结点
	 * @param hasRootNode  是否有默认的root根节点
	 * @return
	 * @throws Exception
	 */
	public static <T> T getObject(Document doc, TypeReference<T> tr, String nodeName, boolean hasRootNode) throws Exception {
		if(nodeName == null || "".equals(nodeName.trim()) || "root".equals(nodeName)){
			hasRootNode = false;
		}
		Element el = null;
		if(hasRootNode){
			el = doc.getRootElement().element(nodeName);
			if(el == null){
				throw new Exception("root下找不到["+nodeName+"]节点！");
			}
		} else {
			el = doc.getRootElement();
		}
		return parseObject(el, tr);
	}
	
	/**
	 * 将DOM对象解析成JavaBean
	 * @param <T>
	 * @param doc          DOM对象
	 * @param tr           JavaBean类型
	 * @param nodeName     以指定的结点做为根结点，为null时表示默认根结点
	 * @return
	 * @throws Exception
	 */
	public static <T> T getObject(Document doc, TypeReference<T> tr, String nodeName) throws Exception {
		return getObject(doc, tr, nodeName, true);
	}
	
	/**
	 * 以指定的类型解析Element元素
	 * @param <T>
	 * @param el    Element元素  
	 * @param tr    指定类型
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseObject(Element el, TypeReference<T> tr) throws Exception {
		return (T)parseObject(el,tr.getEntityType());
	}
	
	/**
	 * 以指定的类型解析Element元素
	 * @param el            Element元素
	 * @param type          指定类型
	 * @return
	 * @throws Exception
	 */
	private static Object parseObject(Element el, Type type) throws Exception {
		if(TypeReference.isGenericArrayType(type)){
			Type arrayType = TypeReference.getGenericArrayType(type);
			return parseArrayObjs(el, arrayType, true);
		} else if(TypeReference.isWildcardType(type)){
			return parseObject(el, TypeReference.Map_TypeReference.getEntityType());
		} else {
			Class<?> c = TypeReference.changeClass(type);
			if(List.class.isAssignableFrom(c)){
				Type ptype = null;
				if(TypeReference.isParameterizedType(type)){
					ptype = TypeReference.getParameterizedType(type)[0];
				} else {
					ptype = TypeReference.Map_TypeReference.getEntityType();
				}
				return parseArrayObjs(el, ptype, false);
			} else if(c.isArray()){
				Class<?> componentType = c.getComponentType();
				return parseArrayObjs(el, componentType, true);
			} else if(Map.class.isAssignableFrom(c)){
				return parseMap(el, type);
			} else {
				return parseObj(el, c);
			}
		}
	}
	
	/**
	 * 解析集合节点
	 * @param parent       集合节点
	 * @param entryType    子节点类型
	 * @param isArray      是否返回数组
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static Object parseArrayObjs(Element parent, Type entryType, boolean isArray) throws Exception {
		if(parent == null) return null;
		Object[] objs = null;
		List<Object> list = null;
		String elName = getElementName(TypeReference.changeClass(entryType));
		List<Element> els = null;
		if(elName == null){
			els = parent.elements();
		} else {
			els = parent.elements(elName);
		}
		if(els != null && els.size() > 0){
			if(isArray){
				objs = new Object[els.size()];
			} else {
				list = new ArrayList<Object>();
			}
			for(int i=0;i<els.size();i++){
				Element e = els.get(i);
				if(isArray){
					objs[i] = parseObject(e,entryType);
				} else {
					list.add(parseObject(e, entryType));
				}
			}
		}
		if(isArray){
			return objs;
		} else {
			return list;
		}
	}
	
	/**
	 * 解析对象结点中的属性
	 * @param parent      解析结点
	 * @param obj         目标对象
	 * @param field       对象成员变量
	 * @return
	 * @throws Exception
	 */
	private static void parseField(Element parent,Object obj, Field field) throws Exception {
		Object value = null;
		Type ftype = null;
		if(field.getType().isArray()){
			ftype = field.getType();
		} else {
			ftype = field.getGenericType();
		}
		Class<?> c = TypeReference.changeClass(ftype);
		String name = getElementName(field, c);
		if(field.isAnnotationPresent(Attribute.class)){
			value = parseNormalObj(parent.attribute(name), c);
		} else if(field.isAnnotationPresent(ChildNodes.class)){
			value = parseObject(parent, ftype);
		} else {
			Element el = parent.element(name);
			if(el != null){
				value = parseObject(el, ftype);
			}
		}
		if(value != null){
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}
	}
	
	/**
	 * 解析对象结点
	 * @param parent      对象结点
	 * @param c           对象类型
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static Object parseMap(Element el, Type mType) throws Exception {
		Map<String,Object> obj = null;
		if(el != null && el.elements().size() > 0){
			Type defaultType = null;
			if(TypeReference.isParameterizedType(mType)){
				defaultType = TypeReference.getParameterizedType(mType)[1];
			}
			List<Element> elements = (List<Element>)el.elements();
			if(elements != null && elements.size() > 0){
				Class<Map<String,Object>> mapClass = (Class<Map<String,Object>>)TypeReference.changeClass(mType);
				if(mapClass.isInterface()){
					obj = new HashMap<String,Object>();
				} else {
					obj = mapClass.newInstance();
				}
				for(Element kv : elements){
					Type valueType = null;
					if(defaultType == null || TypeReference.isWildcardType(defaultType)) {
						if(kv.hasMixedContent()){
							valueType = TypeReference.Map_TypeReference.getEntityType();
						} else {
							valueType = String.class;
						}
					} else {
						valueType = defaultType;
					}
					obj.put(kv.getName(), parseObject(kv, valueType));
				}
			}
		}
		return obj;
	}
	
	/**
	 * 解析对象结点
	 * @param parent       对象结点
	 * @param c            对象类型
	 * @param attributeName  读取指定属性值,为NULL时el是当前节点
	 * @return
	 * @throws Exception
	 */
	private static Object parseObj(Element el,Class<?> c) throws Exception {
		Object obj = null;
		if(ObjXml.class.isAssignableFrom(c)){
			if(el != null){
				obj = c.newInstance();
				List<Field> fields = getFieldsFromClass(c);
				if(fields != null && fields.size() > 0){
					for(Field field : fields){
						parseField(el, obj, field);
					}
				}
			}
		} else {
			obj = parseNormalObj(el, c);
		}
		return obj;
	}
	
	/**
	 * 解析普通节点，作为基本类型解析
	 * @param <T>
	 * @param el           Element元素或org.dom4j.Attribute
	 * @param c            基本类型，文本、数字、boolean和字节
	 * @param attributeName  读取指定属性值,为NULL时el是当前节点
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> T parseNormalObj(Object el, Class<T> c){
		if(el == null) return null;
		String value = null;
		if(Element.class.isAssignableFrom(el.getClass())){
			value = ((Element)el).getTextTrim();
		} else if(org.dom4j.Attribute.class.isAssignableFrom(el.getClass())) {
			value = ((org.dom4j.Attribute)el).getValue();
		}
		if(value == null) return null;
		T t = null;
		if (c.equals(Integer.class) || c.equals(int.class)) {
			t = (T)Integer.valueOf(value);
		} else if (c.equals(Long.class) || c.equals(long.class)) {
			t = (T)Long.valueOf(value);
		} else if (c.equals(String.class)) {
			t = (T)String.valueOf(value);
		} else if (c.equals(Float.class) || c.equals(float.class)) {
			t = (T)Float.valueOf(value);
		} else if (c.equals(Double.class) || c.equals(double.class)) {
			t = (T)Double.valueOf(value);
		} else if (c.equals(Boolean.class) || c.equals(boolean.class)) {
			t = (T)Boolean.valueOf(value);
		} else if (c.equals(Byte.class) || c.equals(byte.class)) {
			t = (T)Byte.valueOf(value);
		}
		return t;
	}
	
	private static void setData(Element el, String data){
		if(el != null && data != null){
			if(use_CData){
				el.addCDATA(data);
			} else {
				el.setText(data);
			}
		}
	}

	/**
	 * 功能说明:根据CLASSNAME获得表名
	 * 
	 * @param classname
	 * @return
	 */
	public static String getElementName(Object obj) {
		return getElementName(obj.getClass());
	}
	
	public static String getElementName(Field field, Class<?> c){
		if(field.isAnnotationPresent(ElementName.class)){
			ElementName en = field.getAnnotation(ElementName.class);
			return en.value();
		} else if(c.isAnnotationPresent(ElementName.class)) {
			ElementName en = c.getAnnotation(ElementName.class);
			return en.value();
		} else {
			return field.getName();
		}
	}
	
	public static String getElementName(Class<?> c){
		if(c.isAnnotationPresent(ElementName.class)){
			ElementName en = c.getAnnotation(ElementName.class);
			return en.value();
		} else if(List.class.isAssignableFrom(c) || Map.class.isAssignableFrom(c)){
			return null;
		} else {
			return getElementName(c.getName());
		}
	}

	public static String getElementName(String classname) {
		return classname.substring(classname.lastIndexOf(".") + 1).toLowerCase();
	}
	
	public static List<Field> getFieldsFromClass(Class<?> c){
		List<Field> fields = new ArrayList<Field>();
		if(c != null && !c.equals(ObjXml.class)){
			if (c.getGenericSuperclass() != null) {
				Class<?> superClass = c.getSuperclass();// 父类
				List<Field> superFields = getFieldsFromClass(superClass);
				fields.addAll(superFields);
			}
			for(Field f : c.getDeclaredFields()){
				fields.add(f);
			}
		}
		return fields;
	}

}
