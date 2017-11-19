package com.unimas.txl.service;

import java.sql.Connection;
import java.util.List;

import com.unimas.jdbc.DBFactory;
import com.unimas.txl.bean.SyzBean;
import com.unimas.txl.dao.SyzDao;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class SyzService {
	
	public List<SyzBean> query(String dqId, String name, ShiroUser user) throws Exception {
		return new SyzDao().query(user.getJigouId(), dqId, name);
	}
	
	public void add(String name, String dqId, String username, String password, ShiroUser user) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			UserService us = new UserService();
			if(us.checkUsername(conn, username)){
				conn.setAutoCommit(false);
				String userNo = us.saveAccount(conn, username, password, "syz");
				SyzBean bean = new SyzBean();
				bean.setJigouId(user.getJigouId());
				bean.setName(name);
				bean.setDqId(dqId);
				bean.setUserNo(userNo);
				bean.setCishu(0);
				bean.setIsDel(0);
				new SyzDao().save(conn, bean);
				conn.commit();
			} else {
				throw new Exception("账号已存在！");
			}
		} catch(Exception e){
			if(conn != null){
				try{ conn.rollback(); }catch(Exception e1){}
			}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void update(int id, String name, String dqId, boolean updatePwd, String userNo, String password) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			if(updatePwd){
				UserService us = new UserService();
				us.updatePwd(userNo, password);
			}
			SyzBean bean = new SyzBean();
			bean.setId(id);
			bean.setName(name);
			bean.setDqId(dqId);
			new SyzDao().save(conn, bean);
			conn.commit();
		} catch(Exception e){
			if(conn != null){
				try{ conn.rollback(); }catch(Exception e1){}
			}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void delete(int id) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			SyzDao dao = new SyzDao();
			SyzBean tmp = new SyzBean();
			tmp.setId(id);
			tmp = (SyzBean)dao.getById(conn, tmp);
			String userNo = tmp.getUserNo();
			new UserService().deleteAccount(conn, userNo);
			SyzBean bean = new SyzBean();
			bean.setId(id);
			bean.setIsDel(1);
			dao.save(conn, bean);
			conn.commit();
		} catch(Exception e){
			if(conn != null){
				try{ conn.rollback(); }catch(Exception e1){}
			}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}

}
