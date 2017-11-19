package com.unimas.txl.service;

import java.sql.Connection;
import java.util.List;

import com.unimas.jdbc.DBFactory;
import com.unimas.txl.bean.LryBean;
import com.unimas.txl.dao.LryDao;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class LryService {
	
	@SuppressWarnings("unchecked")
	public List<LryBean> query(ShiroUser user) throws Exception {
		LryBean bean = new LryBean();
		bean.setIsDel(0);
		bean.setJigouId(user.getJigouId());
		return (List<LryBean>) new LryDao().query(bean);
	}
	
	public void add(String name, String username, String password, ShiroUser user) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			UserService us = new UserService();
			if(us.checkUsername(conn, username)){
				conn.setAutoCommit(false);
				String userNo = us.saveAccount(conn, username, password, "syz");
				LryBean bean = new LryBean();
				bean.setJigouId(user.getJigouId());
				bean.setName(name);
				bean.setUserNo(userNo);
				bean.setIsDel(0);
				new LryDao().save(conn, bean);
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
	
	public void update(int id, String name, boolean updatePwd, String userNo, String password) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			if(updatePwd){
				UserService us = new UserService();
				us.updatePwd(userNo, password);
			}
			LryBean bean = new LryBean();
			bean.setId(id);
			bean.setName(name);
			new LryDao().save(conn, bean);
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
			LryDao dao = new LryDao();
			LryBean tmp = new LryBean();
			tmp.setId(id);
			tmp = (LryBean)dao.getById(conn, tmp);
			String userNo = tmp.getUserNo();
			new UserService().deleteAccount(conn, userNo);
			LryBean bean = new LryBean();
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
