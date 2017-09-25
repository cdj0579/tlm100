package com.unimas.tlm.service.jagl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.tlm.bean.ja.JaBean;
import com.unimas.tlm.bean.ja.JaDirs;
import com.unimas.tlm.bean.ja.JaTemplete;
import com.unimas.tlm.dao.JdbcDao;
import com.unimas.web.auth.AuthRealm.ShiroUser;

@Service
public class JaglService {
	
	private static final int MAX_CONUT = 100;
	
	@SuppressWarnings("unchecked")
	public List<JaBean> getJaList(int dirId, ShiroUser user) throws Exception {
		JaBean bean = new JaBean();
		bean.setUserNo(user.getUserNo());
		if(dirId > 0){
			bean.setDirId(dirId);
		}
		List<JaBean> list = (List<JaBean>)new JdbcDao<JaBean>().query(bean);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<JaDirs> getDirList(int type, ShiroUser user) throws Exception {
		JaDirs dir = new JaDirs();
		dir.setType(type);
		dir.setUserNo(user.getUserNo());
		List<JaDirs> list = (List<JaDirs>)new JdbcDao<JaDirs>().query(dir);
		return list;
	}
	
	public JaDirs saveDir(int id, int pid, int type, String name, ShiroUser user) throws Exception {
		JaDirs dir = new JaDirs();
		dir.setId(id);
		dir.setPid(pid);
		dir.setType(type);
		dir.setUserNo(user.getUserNo());
		dir.setName(name);
		return (JaDirs)new JdbcDao<JaDirs>().save(dir);
	}
	
	public void deleteDir(int id) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select queryChildrenJaDirsInfo("+id+")");
			String ids = ResultSetHandler.toString(rs);
			stmt.execute("delete from ja_dirs where id in ("+ids+")");
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public JaBean getJaInfoById(int id) throws Exception {
		return (JaBean)new JdbcDao<JaBean>().getById(id, JaBean.class);
	}
	
	public void saveJa(int id, int dirId, String name, String content, int isOriginal, int yyfs, int isShare, ShiroUser user) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			JaBean bean = new JaBean();
			JdbcDao<JaBean> dao = new JdbcDao<JaBean>();
			if(id > 0){
				bean.setId(id);
			} else {
				bean.setUserNo(user.getUserNo());
				List<?> list = dao.query(conn, bean);
				if(list.size() >= MAX_CONUT){
					throw new Exception("最多只能保存"+MAX_CONUT+"个教案！");
				}
			}
			bean.setDirId(dirId);
			bean.setName(name);
			bean.setContent(content.getBytes("utf-8"));
			bean.setIsOriginal(isOriginal);
			if(isOriginal == 1){
				bean.setYyfs(yyfs);
			} else {
				bean.setYyfs(1);
			}
			bean.setIsShare(isShare);
			dao.save(conn, bean);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void saveJaTemplete(int id, int dirId, String name, String content, int isOriginal, int yyfs, int isShare, ShiroUser user) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			JaTemplete bean = new JaTemplete();
			JdbcDao<JaTemplete> dao = new JdbcDao<JaTemplete>();
			if(id > 0){
				bean.setId(id);
			} else {
				bean.setUserNo(user.getUserNo());
				List<?> list = dao.query(conn, bean);
				if(list.size() >= MAX_CONUT){
					throw new Exception("最多只能保存"+MAX_CONUT+"个教案模板！");
				}
			}
			bean.setDirId(dirId);
			bean.setName(name);
			bean.setContent(content.getBytes("utf-8"));
			bean.setIsOriginal(isOriginal);
			if(isOriginal == 1){
				bean.setYyfs(yyfs);
			} else {
				bean.setYyfs(1);
			}
			bean.setIsShare(isShare);
			dao.save(conn, bean);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void deleteJa(int id) throws Exception {
		new JdbcDao<JaBean>().delete(id, JaBean.class);
	}
	
	public void deleteJaTemplete(int id) throws Exception {
		new JdbcDao<JaTemplete>().delete(id, JaTemplete.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<JaTemplete> getJaTempletes(int dirId, ShiroUser user) throws Exception {
		JaTemplete bean = new JaTemplete();
		bean.setUserNo(user.getUserNo());
		if(dirId > 0){
			bean.setDirId(dirId);
		}
		List<JaTemplete> list = (List<JaTemplete>)new JdbcDao<JaTemplete>().query(bean);
		return list;
	}
	
	public JaTemplete getJaTempleteInfoById(int id) throws Exception {
		return (JaTemplete)new JdbcDao<JaTemplete>().getById(id, JaTemplete.class);
	}

}
