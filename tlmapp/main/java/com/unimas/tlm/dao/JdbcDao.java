package com.unimas.tlm.dao;

import java.sql.Connection;
import java.util.List;

import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.SelectHandler;
import com.unimas.jdbc.handler.UpdateInsertHandler;
import com.unimas.tlm.bean.JdbcBean;

public class JdbcDao<T> {
	
	public Object save(JdbcBean b) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			return save(conn, b);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	protected Object save(Connection conn, T t) throws Exception{
		JdbcBean b = (JdbcBean)t;
		int id = b.getId();
		if(id > 0){
			UpdateInsertHandler.executeUpdate(conn, b);
		} else {
			id = UpdateInsertHandler.executeInsert(conn, b);
			b.setId(id);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public Object save(Connection conn, JdbcBean b) throws Exception{
		return save(conn, (T)b);
	}
	
	public void delete(int id, Class<T> c) throws Exception{
		T t = c.newInstance(); 
		JdbcBean b = (JdbcBean)t;
		b.setId(id);
		delete(b);
	}
	
	public void delete(Connection conn, int id, Class<T> c) throws Exception{
		T t = c.newInstance(); 
		JdbcBean b = (JdbcBean)t;
		b.setId(id);
		delete(conn, b);
	}
	
	public void delete(JdbcBean b) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			delete(conn, b);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	protected void delete(Connection conn, T t) throws Exception{
		UpdateInsertHandler.executeDelete(conn, t);
	}
	
	@SuppressWarnings("unchecked")
	public void delete(Connection conn, JdbcBean b) throws Exception{
		delete(conn, (T)b);
	}
	
	public Object getById(Connection conn, int id) throws Exception{
		JdbcBean b = new JdbcBean();
		b.setId(id);
		return getById(conn, b);
	}
	
	public Object getById(int id, Class<T> c) throws Exception{
		T t = c.newInstance(); 
		JdbcBean b = (JdbcBean)t;
		b.setId(id);
		return getById(b);
	}
	
	public Object getById(Connection conn, int id, Class<T> c) throws Exception{
		T t = c.newInstance(); 
		JdbcBean b = (JdbcBean)t;
		b.setId(id);
		return getById(conn, b);
	}
	
	@SuppressWarnings("unchecked")
	public Object getById(Connection conn, JdbcBean b) throws Exception{
		List<?> list = query(conn, (T)b);
		if(list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public Object getById(JdbcBean b) throws Exception{
		List<?> list = query(b);
		if(list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	protected List<T> query(Connection conn, T t) throws Exception{
		return SelectHandler.executeSelect(conn, t);
	}
	
	@SuppressWarnings("unchecked")
	public List<?> query(Connection conn, JdbcBean b) throws Exception{
		return query(conn, (T)b);
	}
	
	public List<?> query(JdbcBean b) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			return query(conn, b);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}

}
