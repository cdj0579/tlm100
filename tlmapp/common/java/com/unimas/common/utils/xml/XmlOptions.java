package com.unimas.common.utils.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlOptions {
	private Document doc;
	private Element root;
	private String options[];

	/**
	 * 构造方法
	 * 
	 * @param options
	 */
	public XmlOptions(String options[]) {
		doc = DocumentHelper.createDocument();
		root = doc.addElement("allTypes");
		root.addAttribute("success", "true");
		this.options = options;
	}

	public XmlOptions() {
		doc = DocumentHelper.createDocument();
		root = doc.addElement("allTypes");
		root.addAttribute("success", "true");
		HashMap map = new HashMap();
		map.put("", "所有");
		this.addOption(map);
	}

	/**
	 * 生成xml
	 * 
	 * @return
	 */
	public String asXML() {
		String xml = doc.asXML();
		return xml;
	}

	/**
	 * 添加字典选项值
	 * 
	 * @param map
	 */
	public void addOption(Map map) {
		Object key1 = null;
		Object value1 = null;
		Iterator iterater = map.keySet().iterator();
		while (iterater.hasNext()) {
			key1 = iterater.next();
			value1 = map.get(key1);
			Element option = root.addElement("option");
			Element value = option.addElement("value");
			value.setText((String) key1);
			Element name = option.addElement("name");
			name.setText((String) value1);

		}
	}

	/**
	 * 添加字典选项值
	 * 
	 * @param map
	 */
	public void addOption(List list) {
		String key1 = null;
		String value1 = null;
		Iterator iterater = list.iterator();
		while (iterater.hasNext()) {
			Map map = (Map) iterater.next();
			key1 = (String) map.get(options[0]);
			value1 = (String) map.get(options[1]);
			Element option = root.addElement("option");
			Element value = option.addElement("value");
			value.setText(key1);
			Element name = option.addElement("name");
			name.setText(value1);

		}
	}
}
