package com.unimas.txl.service;

import java.sql.Connection;
import java.util.List;

import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.SelectHandler;
import com.unimas.jdbc.handler.UpdateInsertHandler;
import com.unimas.txl.bean.ConfigBean;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class ConfigService {
	
	public ConfigBean load(ShiroUser user) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			return load(conn, user.getJigouId());
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	protected ConfigBean load(int jigouId) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			return load(conn, jigouId);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public ConfigBean load(Connection conn, int jigouId) throws Exception {
		ConfigBean b = new ConfigBean();
		b.setJigouId(jigouId);
		List<ConfigBean> list = SelectHandler.executeSelect(conn, b);
		if(list != null && list.size() > 0){
			return list.get(0);
		} else {
			ConfigBean bean = new ConfigBean();
			bean.setJigouId(jigouId);
			UpdateInsertHandler.executeInsert(conn, bean);
			return load(conn, jigouId);
		}
	}
	
	public void save(ConfigBean bean) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			UpdateInsertHandler.executeUpdate(conn, bean);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public List<ConfigBean> getAll() throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			return SelectHandler.executeSelect(conn, new ConfigBean());
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void save(int zhouqi, int shichang, int gzShangxian, int shangxian, ShiroUser user) throws Exception {
		ConfigBean bean = new ConfigBean();
		bean.setJigouId(user.getJigouId());
		bean.setShangxian(shangxian);
		bean.setGzShangxian(gzShangxian);
		bean.setShichang(shichang);
		bean.setZhouqi(zhouqi);
		save(bean);
	}
	
	public static void main(String[] args) throws Exception {
		ConfigBean bean = new ConfigBean();
		bean.setJigouId(1);
		//bean.setShangxian(50);
		//bean.setShichang(4);
		bean.setZhouqi(5);
		new ConfigService().save(bean);
		//ConfigBean bean = new ConfigService().load(1);
		//System.out.println(bean);
	}

}
