package com.unimas.common.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ApplicationTools {

	protected static final Logger log = Logger
			.getLogger(ApplicationTools.class);

	private static ApplicationTools instance = new ApplicationTools();

	public static synchronized ApplicationTools getInstance() {
		return instance;
	}

	private Properties appProperty = new Properties();

	public Properties getProperties() {
		return appProperty;
	}

	/**
	 * 注释：获取一个Property文件的Properties 属性
	 * 
	 * @param propertyFileName
	 * @return
	 * @throws IOException
	 */
	public Properties getProperties(String propertyFileName) throws IOException {
		// ClassLoader loader = getClass().getClassLoader();
		Properties properties = new Properties();
		if (new File(propertyFileName).isFile()) {
			properties.load(new FileInputStream(propertyFileName));
		} else {
			if (log.isInfoEnabled()) {
				log.info(propertyFileName + "配置文件不存在!");
			}
		}
		return properties;
	}

	/**
	 * 注释：
	 * 
	 * @param propertyFile
	 * @throws IOException
	 */
	public void setProperties(String propertyFile) throws IOException {
		loadApplicationConfig(propertyFile);
	}

	/**
	 * 注释：把一个Map属性保存到指定的文件当中
	 * 
	 * @param property
	 * @param propertyFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void savePropertiesToFile(Properties property, String propertyFile)
			throws FileNotFoundException, IOException {
		saveApplicationConfig(property, propertyFile);
	}

	public void saveProperties(String propertyFile)
			throws FileNotFoundException, IOException {
		if (appProperty != null) {
			saveApplicationConfig(appProperty, propertyFile);
		}
	}

	/**
	 * 注释：把Property文件家载到当前应用中
	 * 
	 * @param propertyFile
	 * @throws IOException
	 */
	private void loadApplicationConfig(String propertyFile) throws IOException {
		ClassLoader loader = getClass().getClassLoader();
		appProperty.load(loader.getResourceAsStream(propertyFile));
		// appProperty.load(new FileInputStream("C:\\app.properties"));
	}

	/**
	 * 注释：得到健值的值
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		if (appProperty != null) {
			return appProperty.getProperty(key);
		} else {
			return null;
		}
	}

	/**
	 * 注释：设置Map中健值所对应的值
	 * 
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value) {
		if (appProperty != null) {
			appProperty.setProperty(key, value);
		}
	}

	/**
	 * 注释：把Map保存Property文件
	 * 
	 * @param property
	 * @param propertyFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void saveApplicationConfig(Properties property, String propertyFile)
			throws FileNotFoundException, IOException {
		FileOutputStream fileOut = new FileOutputStream(propertyFile);
		property.store(fileOut, null);
		fileOut.close();
	}
}
