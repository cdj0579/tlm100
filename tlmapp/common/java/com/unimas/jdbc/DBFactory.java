package com.unimas.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.unimas.common.file.PropertyUtils;

public class DBFactory {
	
	private static Logger logger = Logger.getLogger(DBFactory.class);
	
	/**
	 * 默认数据库JDBC连接驱动
	 */
	private static final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";
	/**
	 * 默认数据库JDBC连接地址
	 */
	private static final String DEFAULT_url = "jdbc:mysql://121.41.41.4:3306/tlm?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull";
	/**
	 * 默认数据库JDBC连接用户名
	 */
	private static final String DEFAULT_userName = "tlm";
	/**
	 * 默认数据库JDBC连接密码
	 */
	private static final String DEFAULT_password = "tlm2017";
	private static Map<String, String> dbConf = null;
	
	/**
	 * 获取本地默认数据库连接
	 * @return
	 * @throws Exception 
	 */
	public static Connection getConn() throws Exception  {
		initDbConfig();
		String driver = DEFAULT_DRIVER;
		if (dbConf.containsKey("driver")) {
			driver = dbConf.get("driver");
		}
		String url = DEFAULT_url;
		if (dbConf.containsKey("url")) {
			url = dbConf.get("url");
		}
		String username = DEFAULT_userName;
		if (dbConf.containsKey("userName")) {
			username = dbConf.get("userName");
		}
		String password = DEFAULT_password;
		if (dbConf.containsKey("password")) {
			password = dbConf.get("password");
		}
		return getConn(driver, url, username, password);
	}
	
	private static void initDbConfig() {
		try {
			if(dbConf == null || dbConf.isEmpty()){
				dbConf = PropertyUtils.readProperties(DBFactory.class.getClassLoader().getResource("db.properties").toURI().getPath());
			}
		} catch (Exception e) {
			logger.error("加载数据库连接信息失败！", e);
		}
		if(dbConf == null){
			dbConf = new HashMap<String, String>();
		}
	}
	
	/**
	 * 获取数据库连接
	 * @param driver     JDBC驱动
	 * @param url        JDBC连接地址
	 * @param userName   用户名
	 * @param password   密码
	 * @return
	 * @throws Exception
	 */
	public static Connection getConn(String driver, String url, String userName, String password) throws Exception {
		Connection conn = null;
		try {
			Class.forName(driver);
			logger.debug("初始化数据库连接, url="+url);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("初始化数据库连接失败！");
		}
		return conn;
	}
	
	/**
	 * 关闭数据库连接
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public static void close(Connection conn, Statement stmt,  ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
		if(stmt != null){
			try {
				stmt.close();
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
		if(conn != null){
			try {
				conn.close();
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
	}

}
