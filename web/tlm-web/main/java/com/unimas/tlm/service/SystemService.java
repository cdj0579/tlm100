package com.unimas.tlm.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.tlm.bean.HydRule;
import com.unimas.tlm.bean.jfrw.JfRule;
import com.unimas.tlm.dao.JdbcDao;

/**
 * <p>描述: 系统相关服务</p>
 * @author hxs
 * @date 2016年12月29日 上午11:21:14
 */
public class SystemService {
	
	private static Map<String, Object> config = null;
	
	public SystemService(){
		init();
	}

	/**
	 * 获取菜单
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> getMenus(String basePath) throws Exception{
		return new MenuManage(basePath).getMenus();
	}
	
	/**
	 * 获取用户菜单
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> getUserMenus(String basePath) throws Exception{
		return new MenuManage(basePath).getUserMenus();
	}
	
	public static Map<String, Object> loadConfig() throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select value from config where name='ks_pt'");
			int ks_pt = Integer.parseInt(ResultSetHandler.toString(rs));
			Map<String, Object> config = Maps.newHashMap();
			config.put("ks_pt", ks_pt);
			return config;
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public static void saveConfig(int ks_pt) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate("update config set value="+ks_pt+" where name='ks_pt'");
		} catch(Exception e){
			throw e;
		} finally {
			DBFactory.close(conn, stmt, null);
		}
	}
	
	@SuppressWarnings("unchecked")
	public JfRule getJfRuleByType(String type){
		JfRule rule = null;
		if(config.containsKey("jfRules")) {
			List<JfRule> jfRules = (List<JfRule>)config.get("jfRules");
			for(JfRule tmp : jfRules){
				if(tmp.getType().equals(type)){
					rule = tmp;
					break;
				}
			}
		}
		return rule;
	}
	
	@SuppressWarnings("unchecked")
	public HydRule getHydRuleByType(String type){
		HydRule rule = null;
		if(config.containsKey("hydRules")) {
			List<HydRule> hydRules = (List<HydRule>)config.get("hydRules");
			for(HydRule tmp : hydRules){
				if(tmp.getType().equals(type)){
					rule = tmp;
					break;
				}
			}
		}
		return rule;
	}
	
	@SuppressWarnings("unchecked")
	public void init() throws RuntimeException{
		if(config == null){
			try {
				Map<String, Object> map = Maps.newHashMap();
				List<JfRule> jfRules = (List<JfRule>)new JdbcDao<JfRule>().query(new JfRule());
				List<HydRule> hydRules = (List<HydRule>)new JdbcDao<JfRule>().query(new HydRule());
				map.put("jfRules", jfRules);
				map.put("hydRules", hydRules);
				config = map;
			} catch(Exception e){
				throw new RuntimeException("初始化系统配置信息失败！", e);
			}
		}
	}

}
