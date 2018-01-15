package com.unimas.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * 描述: 属性信息工具操作对象
 * 
 * 作者:szpShang <br/>
 * 创建时间:2012-3-7<br/>
 * 版本号:1.0<br/>
 * 版权所有:杭州合众信息工程有限公司
 */
public class PropUtil {
	private Properties p;
	
	public PropUtil(){
		p = new Properties();
	}
	
	
	/**
	 * 初始化属性对象
	 * 保存文件时,需要输入文件路径 否则将出现NullPointerException错误
	 * @param file
	 * @throws IOException
	 */
	public PropUtil(File file) throws IOException {
		this();
		p.load(new FileInputStream(file));
	}
	
	/**
	 * 构造函数
	 * @param inputStream
	 * @throws IOException
	 */
	public PropUtil(InputStream inputStream) throws IOException {
		this();
		p.load(inputStream);
	}
	
	/**
	 * 获取属性值
	 * @param key			字段值
	 * @param defaultValue	默认值
	 * @return
	 */
	public String get(String key, String defaultValue) {
		return p.getProperty(key, defaultValue);
	}

	/**
	 * 获取属性值 
	 * @param key	字段名
	 * @return 如果字段不存在 会有null
	 */
	public String get(String key) {
		return p.getProperty(key);
	}

	/**
	 * 获取数字类型数据
	 * @param key			字段值
	 * @param defaultValue	默认值
	 * @return
	 */
	public int getIntValue(String key, int defaultValue) {
		return Integer.parseInt(get(key, String.valueOf(defaultValue)));
	}

	/**
	 * 获取数字类型数据
	 * @param key	字段值
	 * @return	如果字段不存在 会有null
	 */
	public int getIntValue(String key) {
		return getIntValue(key, 0);
	}

	/**
	 * 获取数字类型数据
	 * @param key			字段值
	 * @param defaultValue	默认值
	 * @return
	 */
	public float getFloatValue(String key, float defaultValue) {
		return Integer.parseInt(get(key, String.valueOf(defaultValue)));
	}

	/**
	 * 获取数字类型数据
	 * @param key	字段值
	 * @return	如果字段不存在 会有null
	 */
	public float getFloatValue(String key) {
		return getFloatValue(key, 0.0f);
	}
	
	
	/**
	 * 获取所有字段集合 
	 * @return
	 */
	public Set<Object> getKeySet() {
		return p.keySet();
	}
	
	/**
	 * 设置属性值
	 * @param key	字段值
	 * @param value	默认值 
	 */
	public void setValue(String key, String value) {
		p.put(key, value);
	}

	/**
	 * 删除字段 
	 * @param key
	 */
	public void remove(String key){
		p.remove(key);
	}
	
	
	/**
	 * 保存文件
	 * @param file	保存文件路径 
	 * @throws IOException
	 */
	public void save(File file) throws IOException {
		p.store(new FileOutputStream(file), "");
	}

	/**
	 * 保存文件 
	 * @param file		保存文件路径
	 * @param comments	附加说明 
	 * @throws IOException
	 */
	public void save(File file, String comments) throws IOException {
		p.store(new FileOutputStream(file), comments);
	}

}
