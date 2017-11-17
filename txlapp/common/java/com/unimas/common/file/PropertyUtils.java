package com.unimas.common.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 操作文件的工具类
 * 
 * @author liuhq
 *
 */
public class PropertyUtils {
	private static final Logger log = Logger.getLogger(PropertyUtils.class);
	
	@SuppressWarnings("rawtypes")
	public static Map<String, String> readProperties(String filePath) {
		Map<String, String> map = new HashMap<String, String>();
		InputStream is = null;
		try {
			Reader br =  new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
			Properties properties = new Properties();
			properties.load(br);

			// 获取所有的key，放入枚举类型中
			Enumeration en = properties.propertyNames();
			// 遍历枚举，根据key取出value
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String value = properties.getProperty(key);
				map.put(key, value);
			}
		} catch (IOException e) {
			log.error("load file:"+filePath+" error,"+e.getMessage());
		} finally {
			if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getPropertyByName(String filePath, String propertyName) {
		String value = null;
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);

			Properties properties = new Properties();
			properties.load(is);

			// 获取所有的key，放入枚举类型中
			Enumeration en = properties.propertyNames();
			// 遍历枚举，根据key取出value
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				if (propertyName.equalsIgnoreCase(key)) {
					value = properties.getProperty(key);
					break;
				}
			}
		} catch (IOException e) {
			log.error("load file:"+filePath+" for parameter:"+propertyName+" error,"+e.getMessage());
		} finally {
			if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	public synchronized static void writeProperties(String filePath, Map<String, String> map) {
		Properties prop = new Properties();
		InputStream fis = null;
		OutputStream fos = null;
		try {
			fis = new FileInputStream(filePath);
			// 调用Hashtable的方法 put。使用getProperty方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是Hashtable调用put的结果。
			fos = new FileOutputStream(filePath);
			if(map != null){
				for(String key : map.keySet()){
					prop.setProperty(key, map.get(key));
				}
			}
			// 以适合使用 load 方法加载到 Properties 表中的格式，将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update value");
		} catch (IOException e) {
			log.error("load file:"+ filePath +" for updating error.");
		} finally {
			if (fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public synchronized static void writeProperties(String filePath,String parameterName,String parameterValue) {
		Properties prop = new Properties();
		InputStream fis = null;
		OutputStream fos = null;
		try {
			fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用Hashtable的方法 put。使用getProperty方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是Hashtable调用put的结果。
			fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			log.error("load file:"+ filePath +" for updating parameterName:"+ parameterName +",parameterValue"+parameterValue+" error.");
		} finally {
			if (fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
